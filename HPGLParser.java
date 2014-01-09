package org.golovin.jtcad.format;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.golovin.jtcad.geometry.Figure;
import org.golovin.jtcad.geometry.Pad;
import org.golovin.jtcad.geometry.Trace;

public class HPGLParser {
	public HPGLParser() {
	}

	public List<Figure> parse(String filename) {
		String all_cmd = "";

		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get(filename),
					StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < lines.size(); i++) {
			String str = lines.get(i);

			str = str.replaceAll("[\t\n\r ]", "");
			str = str.toUpperCase();

			all_cmd += str;
		}

		String[] arr_cmd = all_cmd.split(";");

		int[] last_pupa = { 0, 0 };

		List<Pad> pads = new ArrayList<Pad>();
		List<Trace> traces = new ArrayList<Trace>();

		Figure cur_fig = null;

		for (int i = 0; i < arr_cmd.length; i++) {
			System.out.print(arr_cmd[i]);

			if (arr_cmd[i].equals("PU")) {
				System.out.println("\t: pen up");

				while (!arr_cmd[i].equals("PD") && i < arr_cmd.length - 1) {
					i++;
					if (arr_cmd[i].startsWith("PA") && arr_cmd[i].contains(",")) {
						System.out.print("\t\tPU : PA");

						String[] coordinates = arr_cmd[i]
								.replaceFirst("PA", "").split(",");

						int x = Integer.parseInt(coordinates[0]);
						int y = Integer.parseInt(coordinates[1]);

						System.out.println(" " + x + " " + y);

						last_pupa = new int[] { x, y };
					} else {
						System.out.println("\tPU : " + arr_cmd[i]
						 + "\t: ignoring");
					}
				}
				if (arr_cmd[i].equals("PD")) {
					i--;
				}
			} else if (arr_cmd[i].equals("PD")) {
				System.out.println("\t: pen down");

				cur_fig = new Trace();

				cur_fig.addPoint(last_pupa);

				while (!arr_cmd[i].equals("PU") && i < arr_cmd.length - 1) {
					i++;
					if (arr_cmd[i].startsWith("PA") && arr_cmd[i].contains(",")) {
						System.out.print("\t\tPD : PA");
						String[] coordinates = arr_cmd[i]
								.replaceFirst("PA", "").split(",");

						int x = Integer.parseInt(coordinates[0]);
						int y = Integer.parseInt(coordinates[1]);

						cur_fig.addPoint(new int[] { x, y });

						System.out.println(" " + x + " " + y);
					} else {
						System.out.println("\tPD : " + arr_cmd[i]
						 + "\t: ignoring");
					}
				}

				if (cur_fig != null) {
					if (cur_fig.getPoints().size() == 2
							&& cur_fig.getPoints().get(0) != cur_fig
									.getPoints().get(1)) {
						traces.add((Trace) cur_fig);
						System.out.println(cur_fig.toString()
								+ ": trace added, size "
								+ cur_fig.getPoints().size());

					} else if (cur_fig.getPoints().size() == 5
							&& cur_fig.getPoints().get(0)[0] == cur_fig
									.getPoints().get(
											cur_fig.getPoints().size() - 1)[0]
							&& cur_fig.getPoints().get(0)[1] == cur_fig
									.getPoints().get(
											cur_fig.getPoints().size() - 1)[1]) {
						pads.add(new Pad(cur_fig.getPoints()));
						System.out.println(cur_fig.toString()
								+ ": pad added, size "
								+ cur_fig.getPoints().size());

					} else {
						System.out.println(cur_fig.toString()
								+ ": figure not added, size "
								+ cur_fig.getPoints().size());
					}
				}

				if (arr_cmd[i].equals("PU")) {
					i--; // some kind of black magic
					// I can not remember why do I need this
				}
			} else {
				System.out.println("\t: ignoring");
			}
		}

		List<Figure> figs = new ArrayList<Figure>();

		figs.addAll(traces);
		figs.addAll(pads);

		return figs;
	}
}
