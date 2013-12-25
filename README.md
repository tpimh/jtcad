JtCAD
=====

JtCAD is GTK-based Java CAD.

Actually it is not really a CAD, but a helper processing for plotter files exported from KiCAD or other real CAD software.

For now it is only capable of viewing HPGL files, but later I'll add processing capabilities (with CGAL) and outputing the results.

How to use it?
-----
1. Find an HPGL file (usually outputed by KiCAD), name it "example.plt" and put it in the current directory.
2. Get rid of Java packages (or sort all the source files by apropriate directories):
```
sed -i '/org.golovin.jtcad/d' *.java
```
3. You will need *java-gnome* in your classpath to compile it:
```
javac -classpath /usr/share/java/gtk.jar -sourcepath . CADWindow.java
```
4. Now launch it:
```
java -classpath .:/usr/share/java/gtk.jar CADWindow
```

Now you can view your HPGL file, that's all.
