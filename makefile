make:
	@javac lib/*.java
	@mv lib/*.class .
	@javac Interface.java
	@javac InterfaceGUI.java

clean:
	@rm *.class
