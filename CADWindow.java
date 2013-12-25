package org.golovin.jtcad.intface;

import java.util.List;

import org.freedesktop.cairo.Antialias;
import org.freedesktop.cairo.Context;
import org.gnome.gdk.Event;
import org.gnome.gdk.EventConfigure;
import org.gnome.gtk.CellRendererText;
import org.gnome.gtk.DataColumn;
import org.gnome.gtk.DataColumnString;
import org.gnome.gtk.DrawingArea;
import org.gnome.gtk.Gtk;
import org.gnome.gtk.HBox;
import org.gnome.gtk.ListStore;
import org.gnome.gtk.ScrolledWindow;
import org.gnome.gtk.SelectionMode;
import org.gnome.gtk.SeparatorToolItem;
import org.gnome.gtk.Statusbar;
import org.gnome.gtk.Stock;
import org.gnome.gtk.ToolButton;
import org.gnome.gtk.Toolbar;
import org.gnome.gtk.TreeIter;
import org.gnome.gtk.TreeSelection;
import org.gnome.gtk.TreeSelection.Changed;
import org.gnome.gtk.TreeView;
import org.gnome.gtk.TreeViewColumn;
import org.gnome.gtk.VBox;
import org.gnome.gtk.Widget;
import org.gnome.gtk.Window;
import org.gnome.gtk.WindowPosition;
import org.golovin.jtcad.format.HPGLParser;
import org.golovin.jtcad.geometry.Figure;

public class CADWindow extends Window {

	private List<Figure> figures;

	private HPGLParser hpgl = new HPGLParser();

	int[] selectedPoint = null;

	int[] windowSize = { 0, 0 };

	final View view = new View();

	public CADWindow() {

		figures = hpgl.parse("example.plt");

		setTitle("JtCAD");

		initUI();

		connect(new Window.DeleteEvent() {
			public boolean onDeleteEvent(Widget source, Event event) {
				Gtk.mainQuit();
				return false;
			}
		});

		connect(new Window.ConfigureEvent() {
			public boolean onConfigureEvent(Widget source, EventConfigure event) {

				int[] newSize = { event.getWidth(), event.getHeight() };

				if (windowSize[0] == 0 && windowSize[1] == 0) {
					System.out.println("First size: " + newSize[0] + "x"
							+ newSize[1]);
					windowSize = newSize;
				} else {
					if (windowSize[0] != newSize[0]
							|| windowSize[1] != newSize[1]) {
						System.out.println("Size changed: " + newSize[0] + "x"
								+ newSize[1]);
						windowSize = newSize;
					}
				}

				return false;
			}
		});

		setDefaultSize(800, 600);
		setSizeRequest(800, 600);

		setPosition(WindowPosition.CENTER);
		showAll();
	}

	ScrolledWindow scroll_figures;
	TreeView lview_figures, lview_points;
	ListStore model_figures, model_points;
	Statusbar sbar;
	DrawingArea darea;
	Toolbar toolbar;

	HBox hbox = new HBox(false, 3);
	VBox vbox = new VBox(false, 0);

	public void initUI() {

		initPointView();
		initScroll();
		setPointModel(figures.get(0));
		initFigureView();
		initCanvas();
		initStatusbar();
		initToolbar();

		// hbox.add(lview_figures);
		scroll_figures.add(lview_figures);
		hbox.packStart(scroll_figures, false, false, 0);
		hbox.packStart(lview_points, false, false, 0);
		hbox.packStart(darea, true, true, 0);
		vbox.packStart(toolbar, false, false, 0);
		vbox.packStart(hbox, true, true, 0);
		vbox.packStart(sbar, false, false, 0);
		add(vbox);

	}

	private void initScroll() {
		scroll_figures = new ScrolledWindow();
		scroll_figures.setCanFocus(false);
		scroll_figures.setCanDefault(false);
		scroll_figures.setSizeRequest(150, 150);
	}

	private void initFigureView() {
		TreeIter row;
		CellRendererText renderer;
		TreeViewColumn column;

		final DataColumnString figCol;
		model_figures = new ListStore(
				new DataColumn[] { figCol = new DataColumnString() });

		lview_figures = new TreeView(model_figures);
		lview_figures.setCanFocus(false);
		lview_figures.setCanDefault(false);
		lview_figures.setSizeRequest(150, 150);

		for (Figure fig : figures) {
			row = model_figures.appendRow();
			model_figures.setValue(row, figCol, fig.toString());
		}

		column = lview_figures.appendColumn();
		column.setTitle("Figure list");
		renderer = new CellRendererText(column);
		renderer.setText(figCol);

		/*
		 * old update of points lview_figures.connect(new
		 * TreeView.RowActivated() { public void onRowActivated(TreeView
		 * treeView, TreePath treePath, TreeViewColumn treeViewColumn) {
		 * 
		 * final TreeIter row; final String figStr;
		 * 
		 * row = model_figures.getIter(treePath);
		 * 
		 * figStr = model_figures.getValue(row, figCol);
		 * 
		 * for (Figure fig : figures) { if (fig.toString().equals(figStr)) {
		 * setPointModel(fig); } }
		 * 
		 * } });
		 */

		TreeSelection selection = lview_figures.getSelection();
		selection.setMode(SelectionMode.SINGLE);

		selection.connect(new Changed() {

			public void onChanged(TreeSelection sel) {

				final TreeIter row = sel.getSelected();
				final String figStr;

				figStr = model_figures.getValue(row, figCol);

				for (Figure fig : figures) {
					if (fig.toString().equals(figStr)) {
						fig.setActive(true);

						// new update of points
						setPointModel(fig);
					} else {
						fig.setActive(false);
					}
				}

				darea.queueDraw();

				sbar.setMessage("Selected: " + figStr);
			}
		});
	}

	private void setPointModel(Figure figure) {
		for (TreeViewColumn col : lview_points.getColumns()) {
			lview_points.removeColumn(col);
		}

		TreeIter row;
		CellRendererText xRenderer, yRenderer;
		TreeViewColumn xColumn, yColumn;

		List<int[]> points = figure.getPoints();

		final DataColumnString xCol, yCol;
		model_points = new ListStore(new DataColumn[] {
				xCol = new DataColumnString(), yCol = new DataColumnString() });

		lview_points.setModel(model_points);

		for (int[] point : points) {
			row = model_points.appendRow();
			model_points.setValue(row, xCol, String.valueOf(point[0]));
			model_points.setValue(row, yCol, String.valueOf(point[1]));
		}

		xColumn = lview_points.appendColumn();
		xColumn.setTitle("X");
		xRenderer = new CellRendererText(xColumn);
		xRenderer.setText(xCol);
		// xRenderer.setEditable(true);

		yColumn = lview_points.appendColumn();
		yColumn.setTitle("Y");
		yRenderer = new CellRendererText(yColumn);
		yRenderer.setText(yCol);
		// yRenderer.setEditable(true);

		TreeSelection selection = lview_points.getSelection();
		selection.setMode(SelectionMode.SINGLE);

		selection.connect(new Changed() {

			public void onChanged(TreeSelection sel) {

				final TreeIter row = sel.getSelected();

				if (row != null) {
					selectedPoint = new int[] {
							Integer.valueOf(model_points.getValue(row, xCol)),
							Integer.valueOf(model_points.getValue(row, yCol)) };
				} else {
					selectedPoint = null;
				}

				darea.queueDraw();

			}
		});

	}

	private void initPointView() {
		lview_points = new TreeView();
		lview_points.setCanFocus(false);
		lview_points.setCanDefault(false);
		lview_points.setSizeRequest(80, 100);
	}

	private void initCanvas() {
		view.findOffset(figures);

		darea = new DrawingArea();
		darea.setCanFocus(false);
		darea.setCanDefault(false);
		darea.setSizeRequest(700, 500);

		darea.connect(new Widget.Draw() {
			public boolean onDraw(Widget source, Context cr) {

				cr.setAntialias(Antialias.SUBPIXEL);

				for (Figure fig : figures)
					view.draw(fig, cr);

				if (selectedPoint != null) {
					view.drawPoint(selectedPoint, cr);
				}

				return false;
			}
		});
	}

	private void initStatusbar() {
		sbar = new Statusbar();
		sbar.setCanFocus(false);
		sbar.setCanDefault(false);
	}

	private void initToolbar() {
		toolbar = new Toolbar();

		toolbar.setCanDefault(false);
		toolbar.setCanFocus(false);
		toolbar.setExpandHorizontal(false);
		toolbar.setExpandVertical(false);

		ToolButton left_tb = new ToolButton(Stock.GO_BACK);
		ToolButton down_tb = new ToolButton(Stock.GO_DOWN);
		ToolButton up_tb = new ToolButton(Stock.GO_UP);
		ToolButton right_tb = new ToolButton(Stock.GO_FORWARD);
		ToolButton zoom_in_tb = new ToolButton(Stock.ZOOM_IN);
		ToolButton zoom_out_tb = new ToolButton(Stock.ZOOM_OUT);
		ToolButton zoom_100_tb = new ToolButton(Stock.ZOOM_100);
		ToolButton zoom_fit_tb = new ToolButton(Stock.ZOOM_FIT);
		ToolButton quit_tb = new ToolButton(Stock.QUIT);

		toolbar.insert(left_tb, 0);
		toolbar.insert(down_tb, 1);
		toolbar.insert(up_tb, 2);
		toolbar.insert(right_tb, 3);
		toolbar.insert(new SeparatorToolItem(), 4);
		toolbar.insert(zoom_in_tb, 5);
		toolbar.insert(zoom_out_tb, 6);
		toolbar.insert(zoom_100_tb, 7);
		toolbar.insert(zoom_fit_tb, 8);
		toolbar.insert(new SeparatorToolItem(), 9);
		toolbar.insert(quit_tb, 10);

		left_tb.connect(new ToolButton.Clicked() {

			public void onClicked(ToolButton arg0) {
				view.setOffset(view.getOffset()[0] - 100, view.getOffset()[1]);
				darea.queueDraw();
			}
		});

		right_tb.connect(new ToolButton.Clicked() {

			public void onClicked(ToolButton arg0) {
				view.setOffset(view.getOffset()[0] + 100, view.getOffset()[1]);
				darea.queueDraw();
			}
		});

		up_tb.connect(new ToolButton.Clicked() {

			public void onClicked(ToolButton arg0) {
				view.setOffset(view.getOffset()[0], view.getOffset()[1] - 100);
				darea.queueDraw();
			}
		});

		down_tb.connect(new ToolButton.Clicked() {

			public void onClicked(ToolButton arg0) {
				view.setOffset(view.getOffset()[0], view.getOffset()[1] + 100);
				darea.queueDraw();
			}
		});

		zoom_in_tb.connect(new ToolButton.Clicked() {

			public void onClicked(ToolButton arg0) {
				view.setScale(view.getScale() + 0.05d);
				darea.queueDraw();
			}
		});

		zoom_out_tb.connect(new ToolButton.Clicked() {

			public void onClicked(ToolButton arg0) {
				view.setScale(view.getScale() - 0.05d);
				darea.queueDraw();
			}
		});
		
		zoom_100_tb.connect(new ToolButton.Clicked() {

			public void onClicked(ToolButton arg0) {
				view.setScale(1.0D);
				darea.queueDraw();
			}
		});
		
		zoom_fit_tb.connect(new ToolButton.Clicked() {

			public void onClicked(ToolButton arg0) {
				// TODO: now using data from DrawingArea, but the behavior is strange
				view.fitAll(darea.getAllocatedWidth(), darea.getAllocatedHeight());
				darea.queueDraw();
			}
		});

		quit_tb.connect(new ToolButton.Clicked() {

			public void onClicked(ToolButton toolButton) {
				Gtk.mainQuit();
			}
		});
	}

	public static void main(String[] args) {
		Gtk.init(args);
		new CADWindow();
		Gtk.main();
	}
}
