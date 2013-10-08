import org.freedesktop.cairo.Antialias;
import org.freedesktop.cairo.Context;
import org.gnome.gdk.Event;
import org.gnome.gtk.CellRendererText;
import org.gnome.gtk.DataColumn;
import org.gnome.gtk.DataColumnString;
import org.gnome.gtk.DrawingArea;
import org.gnome.gtk.Gtk;
import org.gnome.gtk.HBox;
import org.gnome.gtk.ListStore;
import org.gnome.gtk.SelectionMode;
import org.gnome.gtk.Statusbar;
import org.gnome.gtk.TreeIter;
import org.gnome.gtk.TreePath;
import org.gnome.gtk.TreeSelection;
import org.gnome.gtk.TreeSelection.Changed;
import org.gnome.gtk.TreeView;
import org.gnome.gtk.TreeViewColumn;
import org.gnome.gtk.VBox;
import org.gnome.gtk.Widget;
import org.gnome.gtk.Window;
import org.gnome.gtk.WindowPosition;

public class CADWindow extends Window {

	private Figure[] figures = {
			new Figure(new int[][] { { 1153, 1243 }, { 1146, 1260 },
					{ 1146, 1686 }, { 1153, 1704 }, { 1167, 1717 },
					{ 1184, 1725 }, { 1864, 1725 }, { 1881, 1717 },
					{ 1895, 1704 }, { 1902, 1686 }, { 1902, 1260 },
					{ 1895, 1243 }, { 1881, 1229 }, { 1864, 1222 },
					{ 1184, 1222 }, { 1167, 1229 }, { 1153, 1243 } }),
			new Figure(new int[][] { { 1674, 1468 }, { 1583, 1468 },
					{ 1580, 1467 }, { 1578, 1465 }, { 1576, 1462 },
					{ 1576, 1434 }, { 1578, 1431 }, { 1580, 1429 },
					{ 1583, 1428 }, { 1651, 1428 }, { 1710, 1418 },
					{ 1711, 1411 }, { 1714, 1404 }, { 1718, 1398 },
					{ 1724, 1393 }, { 1730, 1389 }, { 1737, 1386 },
					{ 1744, 1384 }, { 1812, 1384 }, { 1819, 1386 },
					{ 1826, 1389 }, { 1832, 1393 }, { 1838, 1398 },
					{ 1842, 1404 }, { 1845, 1411 }, { 1846, 1419 },
					{ 1846, 1426 }, { 1845, 1434 }, { 1842, 1440 },
					{ 1838, 1447 }, { 1832, 1452 }, { 1826, 1456 },
					{ 1819, 1459 }, { 1812, 1461 }, { 1744, 1461 },
					{ 1737, 1459 }, { 1730, 1456 }, { 1724, 1452 },
					{ 1720, 1449 }, { 1681, 1455 }, { 1681, 1462 },
					{ 1680, 1465 }, { 1677, 1467 }, { 1674, 1468 } }),
			new Figure(new int[][] { { 1583, 1519 }, { 1580, 1518 },
					{ 1578, 1515 }, { 1576, 1512 }, { 1576, 1485 },
					{ 1578, 1482 }, { 1580, 1480 }, { 1583, 1478 },
					{ 1674, 1478 }, { 1677, 1480 }, { 1680, 1482 },
					{ 1681, 1485 }, { 1681, 1491 }, { 1720, 1498 },
					{ 1724, 1494 }, { 1730, 1490 }, { 1737, 1487 },
					{ 1744, 1486 }, { 1812, 1486 }, { 1819, 1487 },
					{ 1826, 1490 }, { 1832, 1494 }, { 1838, 1500 },
					{ 1842, 1506 }, { 1845, 1513 }, { 1846, 1520 },
					{ 1846, 1528 }, { 1845, 1535 }, { 1842, 1542 },
					{ 1838, 1548 }, { 1832, 1554 }, { 1826, 1558 },
					{ 1819, 1561 }, { 1812, 1562 }, { 1744, 1562 },
					{ 1737, 1561 }, { 1730, 1558 }, { 1724, 1554 },
					{ 1718, 1548 }, { 1714, 1542 }, { 1711, 1535 },
					{ 1710, 1529 }, { 1651, 1519 }, { 1583, 1519 } }),
			new Figure(new int[][] { { 1465, 1468 }, { 1374, 1468 },
					{ 1371, 1467 }, { 1368, 1465 }, { 1367, 1462 },
					{ 1367, 1458 }, { 1326, 1450 }, { 1324, 1452 },
					{ 1318, 1456 }, { 1311, 1459 }, { 1304, 1461 },
					{ 1236, 1461 }, { 1229, 1459 }, { 1222, 1456 },
					{ 1216, 1452 }, { 1210, 1447 }, { 1206, 1440 },
					{ 1203, 1434 }, { 1202, 1426 }, { 1202, 1419 },
					{ 1203, 1411 }, { 1206, 1404 }, { 1210, 1398 },
					{ 1216, 1393 }, { 1222, 1389 }, { 1229, 1386 },
					{ 1236, 1384 }, { 1304, 1384 }, { 1311, 1386 },
					{ 1318, 1389 }, { 1324, 1393 }, { 1330, 1398 },
					{ 1334, 1404 }, { 1337, 1411 }, { 1338, 1419 },
					{ 1338, 1420 }, { 1378, 1428 }, { 1465, 1428 },
					{ 1468, 1429 }, { 1470, 1431 }, { 1472, 1434 },
					{ 1472, 1462 }, { 1470, 1465 }, { 1468, 1467 },
					{ 1465, 1468 } }),
			new Figure(new int[][] { { 1465, 1570 }, { 1415, 1570 },
					{ 1335, 1610 }, { 1337, 1614 }, { 1338, 1622 },
					{ 1338, 1629 }, { 1337, 1637 }, { 1334, 1644 },
					{ 1330, 1650 }, { 1324, 1655 }, { 1318, 1659 },
					{ 1311, 1662 }, { 1304, 1664 }, { 1236, 1664 },
					{ 1229, 1662 }, { 1222, 1659 }, { 1216, 1655 },
					{ 1210, 1650 }, { 1206, 1644 }, { 1203, 1637 },
					{ 1202, 1629 }, { 1202, 1622 }, { 1203, 1614 },
					{ 1206, 1608 }, { 1210, 1601 }, { 1216, 1596 },
					{ 1222, 1592 }, { 1229, 1589 }, { 1236, 1587 },
					{ 1304, 1587 }, { 1308, 1588 }, { 1367, 1558 },
					{ 1367, 1536 }, { 1368, 1533 }, { 1371, 1530 },
					{ 1374, 1529 }, { 1465, 1529 }, { 1468, 1530 },
					{ 1470, 1533 }, { 1472, 1536 }, { 1472, 1563 },
					{ 1470, 1566 }, { 1468, 1568 }, { 1465, 1570 } }),
			new Figure(new int[][] { { 1744, 1283 }, { 1812, 1283 },
					{ 1819, 1284 }, { 1826, 1287 }, { 1832, 1291 },
					{ 1838, 1296 }, { 1842, 1303 }, { 1845, 1310 },
					{ 1846, 1317 }, { 1846, 1325 }, { 1845, 1332 },
					{ 1842, 1339 }, { 1838, 1345 }, { 1832, 1350 },
					{ 1826, 1355 }, { 1819, 1357 }, { 1812, 1359 },
					{ 1744, 1359 }, { 1740, 1358 }, { 1681, 1388 },
					{ 1681, 1411 }, { 1680, 1414 }, { 1677, 1416 },
					{ 1674, 1417 }, { 1583, 1417 }, { 1580, 1416 },
					{ 1578, 1414 }, { 1576, 1411 }, { 1576, 1383 },
					{ 1578, 1380 }, { 1580, 1378 }, { 1583, 1377 },
					{ 1633, 1377 }, { 1713, 1336 }, { 1711, 1332 },
					{ 1710, 1325 }, { 1710, 1317 }, { 1711, 1310 },
					{ 1714, 1303 }, { 1718, 1296 }, { 1724, 1291 },
					{ 1730, 1287 }, { 1737, 1284 }, { 1744, 1283 } }),
			new Figure(new int[][] { { 1744, 1587 }, { 1812, 1587 },
					{ 1819, 1589 }, { 1826, 1592 }, { 1832, 1596 },
					{ 1838, 1601 }, { 1842, 1608 }, { 1845, 1614 },
					{ 1846, 1622 }, { 1846, 1629 }, { 1845, 1637 },
					{ 1842, 1644 }, { 1838, 1650 }, { 1832, 1655 },
					{ 1826, 1659 }, { 1819, 1662 }, { 1812, 1664 },
					{ 1744, 1664 }, { 1737, 1662 }, { 1730, 1659 },
					{ 1724, 1655 }, { 1718, 1650 }, { 1714, 1644 },
					{ 1711, 1637 }, { 1710, 1629 }, { 1710, 1622 },
					{ 1711, 1614 }, { 1713, 1610 }, { 1633, 1570 },
					{ 1583, 1570 }, { 1580, 1568 }, { 1578, 1566 },
					{ 1576, 1563 }, { 1576, 1536 }, { 1578, 1533 },
					{ 1580, 1530 }, { 1583, 1529 }, { 1674, 1529 },
					{ 1677, 1530 }, { 1680, 1533 }, { 1681, 1536 },
					{ 1681, 1558 }, { 1740, 1588 }, { 1744, 1587 } }),
			new Figure(new int[][] { { 1304, 1359 }, { 1236, 1359 },
					{ 1229, 1357 }, { 1222, 1355 }, { 1216, 1350 },
					{ 1210, 1345 }, { 1206, 1339 }, { 1203, 1332 },
					{ 1202, 1325 }, { 1202, 1317 }, { 1203, 1310 },
					{ 1206, 1303 }, { 1210, 1296 }, { 1216, 1291 },
					{ 1222, 1287 }, { 1229, 1284 }, { 1236, 1283 },
					{ 1304, 1283 }, { 1311, 1284 }, { 1318, 1287 },
					{ 1324, 1291 }, { 1330, 1296 }, { 1334, 1303 },
					{ 1337, 1310 }, { 1338, 1317 }, { 1338, 1325 },
					{ 1337, 1332 }, { 1335, 1336 }, { 1415, 1377 },
					{ 1465, 1377 }, { 1468, 1378 }, { 1470, 1380 },
					{ 1472, 1383 }, { 1472, 1411 }, { 1470, 1414 },
					{ 1468, 1416 }, { 1465, 1417 }, { 1374, 1417 },
					{ 1371, 1416 }, { 1368, 1414 }, { 1367, 1411 },
					{ 1367, 1388 }, { 1308, 1358 }, { 1304, 1359 } }),
			new Figure(new int[][] { { 1304, 1562 }, { 1236, 1562 },
					{ 1229, 1561 }, { 1222, 1558 }, { 1216, 1554 },
					{ 1210, 1548 }, { 1206, 1542 }, { 1203, 1535 },
					{ 1202, 1528 }, { 1202, 1520 }, { 1203, 1513 },
					{ 1206, 1506 }, { 1210, 1500 }, { 1216, 1494 },
					{ 1222, 1490 }, { 1229, 1487 }, { 1236, 1486 },
					{ 1304, 1486 }, { 1311, 1487 }, { 1318, 1490 },
					{ 1324, 1494 }, { 1326, 1496 }, { 1367, 1488 },
					{ 1367, 1485 }, { 1368, 1482 }, { 1371, 1480 },
					{ 1374, 1478 }, { 1465, 1478 }, { 1468, 1480 },
					{ 1470, 1482 }, { 1472, 1485 }, { 1472, 1512 },
					{ 1470, 1515 }, { 1468, 1518 }, { 1465, 1519 },
					{ 1378, 1519 }, { 1338, 1527 }, { 1338, 1528 },
					{ 1337, 1535 }, { 1334, 1542 }, { 1330, 1548 },
					{ 1324, 1554 }, { 1318, 1558 }, { 1311, 1561 },
					{ 1304, 1562 } }) };

	int[] selectedPoint = { 1150, 1150 };

	public CADWindow() {

		setTitle("JtCAD");

		initUI();

		connect(new Window.DeleteEvent() {
			public boolean onDeleteEvent(Widget source, Event event) {
				Gtk.mainQuit();
				return false;
			}
		});

		setDefaultSize(800, 600);
		setSizeRequest(800, 600);

		setPosition(WindowPosition.CENTER);
		showAll();
	}

	TreeView lview_figures, lview_points;
	ListStore model_figures, model_points;
	Statusbar sbar;
	DrawingArea darea;

	HBox hbox = new HBox(false, 3);
	VBox vbox = new VBox(false, 0);

	public void initUI() {

		initPointView();
		setPointModel(figures[0]);
		initFigureView();
		initCanvas();
		initStatusbar();

		hbox.add(lview_figures);
		hbox.add(lview_points);

		hbox.add(darea);
		vbox.add(hbox);
		vbox.packStart(sbar, false, false, 0);
		add(vbox);

	}

	/*
	 * TODO: private void initLayerView() {
	 * 
	 * }
	 */

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
		lview_figures.setSizeRequest(100, 100);

		for (Figure fig : figures) {
			row = model_figures.appendRow();
			model_figures.setValue(row, figCol, fig.toString());
		}

		column = lview_figures.appendColumn();
		column.setTitle("Figure list");
		renderer = new CellRendererText(column);
		renderer.setText(figCol);

		lview_figures.connect(new TreeView.RowActivated() {
			public void onRowActivated(TreeView treeView, TreePath treePath,
					TreeViewColumn treeViewColumn) {

				final TreeIter row;
				final String figStr;

				row = model_figures.getIter(treePath);

				figStr = model_figures.getValue(row, figCol);

				for (Figure fig : figures) {
					if (fig.toString().equals(figStr)) {
						setPointModel(fig);
					}
				}

			}
		});

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

		int[][] points = figure.getPoints();

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
		xRenderer.setEditable(true);

		yColumn = lview_points.appendColumn();
		yColumn.setTitle("Y");
		yRenderer = new CellRendererText(yColumn);
		yRenderer.setText(yCol);
		yRenderer.setEditable(true);

		lview_points.connect(new TreeView.RowActivated() {
			public void onRowActivated(TreeView treeView, TreePath treePath,
					TreeViewColumn treeViewColumn) {

				final TreeIter row;

				row = model_points.getIter(treePath);

				selectedPoint = new int[] {
						Integer.valueOf(model_points.getValue(row, xCol)),
						Integer.valueOf(model_points.getValue(row, yCol)) };

				 darea.queueDraw();

			}
		});

	}

	private void initPointView() {
		lview_points = new TreeView();
		lview_points.setCanFocus(false);
		lview_points.setCanDefault(false);
		lview_points.setSizeRequest(80, 100);

		/*
		 * TreeSelection selection = lview_points.getSelection();
		 * selection.setMode(SelectionMode.SINGLE);
		 * 
		 * selection.connect(new Changed() {
		 * 
		 * public void onChanged(TreeSelection sel) {
		 * 
		 * final TreeIter row = sel.getSelected(); final String figStr;
		 * 
		 * figStr = model_points.getValue(row, figCol);
		 * 
		 * for (Figure fig : figures) { if (fig.toString().equals(figStr)) {
		 * fig.setActive(true); } else { fig.setActive(false); } }
		 * 
		 * darea.queueDraw();
		 * 
		 * sbar.setMessage("Selected: " + figStr); } });
		 */
	}

	private void initCanvas() {
		final View view = new View();

		darea = new DrawingArea();
		darea.setCanFocus(false);
		darea.setCanDefault(false);
		darea.setSizeRequest(700, 0);

		darea.connect(new Widget.Draw() {
			public boolean onDraw(Widget source, Context cr) {

				cr.setAntialias(Antialias.SUBPIXEL);

				for (Figure fig : figures)
					view.draw(fig, cr);

				view.drawPoint(selectedPoint, cr);

				return false;
			}
		});
	}

	private void initStatusbar() {
		sbar = new Statusbar();
		sbar.setCanFocus(false);
		sbar.setCanDefault(false);
	}

	public static void main(String[] args) {
		Gtk.init(args);
		new CADWindow();
		Gtk.main();
	}
}
