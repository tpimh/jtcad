package org.golovin.jtcad.format;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.golovin.jtcad.geometry.Figure;
import org.golovin.jtcad.geometry.Pad;
import org.golovin.jtcad.geometry.RPad;
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

			str = str.replaceAll("[\n\r ]", "");
			str = str.toUpperCase();

			all_cmd += str;
		}

		String[] arr_cmd = all_cmd.split(";");

		int[] last_pupa = { 0, 0 };

		List<RPad> rpads = new ArrayList<RPad>();
		List<Pad> pads = new ArrayList<Pad>();
		List<Trace> traces = new ArrayList<Trace>();

		for (int i = 0; i < arr_cmd.length; i++) {
			System.out.print(arr_cmd[i]);

			if (arr_cmd[i].equals("PU")) {
				System.out.println(": pen up");

				while (!arr_cmd[i].equals("PD") && i < arr_cmd.length - 1) {
					i++;
					if (arr_cmd[i].startsWith("PA") && arr_cmd[i].contains(",")) {
						System.out.print("PU : PA");

						String[] coordinates = arr_cmd[i]
								.replaceFirst("PA", "").split(",");

						int x = Integer.parseInt(coordinates[0]);
						int y = Integer.parseInt(coordinates[1]);

						System.out.println(" " + x + " " + y);

						last_pupa = new int[] { x, y };
					} else if (arr_cmd[i].startsWith("CI")) {
						System.out.println("PU : " + arr_cmd[i]
								+ ": circle");
						rpads.add(new RPad(last_pupa, Integer.parseInt(arr_cmd[i]
								.replaceFirst("CI", ""))));
					} else  {
						System.out.println("PU : " + arr_cmd[i] + ": ignoring");
					}
				}
				if (arr_cmd[i].equals("PD")) {
					i--;
				}
			} else if (arr_cmd[i].equals("PD")) {
				System.out.println(": pen down");

				int[][] points = new int[][] { last_pupa, { 0, 0 }, { 0, 0 },
						{ 0, 0 }, { 0, 0 } };
				int iterator = 0;
				int radius = 0;

				while (!arr_cmd[i].equals("PU") && i < arr_cmd.length - 1) {
					i++;
					if (arr_cmd[i].startsWith("PA") && arr_cmd[i].contains(",")) {
						System.out.print("PD : PA");
						String[] coordinates = arr_cmd[i]
								.replaceFirst("PA", "").split(",");

						int x = Integer.parseInt(coordinates[0]);
						int y = Integer.parseInt(coordinates[1]);

						iterator++;

						points[iterator][0] = x;
						points[iterator][1] = y;

						System.out.println(" " + x + " " + y);
					} else if (arr_cmd[i].startsWith("AA")) {
						System.out.println("PD : " + arr_cmd[i] + ": arc");
						String[] coordinates = arr_cmd[i]
								.replaceFirst("AA", "").split(",");

						if (coordinates[2].equals("180")) {
							radius = last_pupa[0]
									- Integer.parseInt(coordinates[0]);
							
							if (radius < 0) {
								radius = 0;
							} else {
								points[0][0] -= radius;
							}

							// add
						} else {
							System.out.println("ignoring: angle is not 180");
						}
					} else {
						System.out.println("PD : " + arr_cmd[i] + ": ignoring");
					}
				}

				if (radius != 0) {
					rpads.add(new RPad(points[0], radius));
				} else if (iterator == 4) {
					pads.add(new Pad(points[0], points[1], points[2],
							points[3]));
				} else if (iterator == 1) {
					traces.add(new Trace(points[0], points[1]));
				} else {
					System.out.println("WTF?");
				}

				if (arr_cmd[i].equals("PU")) {
					i--; // some kind of black magic
					// I can not remember why do I need this
				}
			} else {
				
				System.out.println(": ignoring");
			}
		}

		List<Figure> figs = new ArrayList<Figure>();

		figs.addAll(traces);
		figs.addAll(pads);
		figs.addAll(rpads);

		return figs;
	}
}
