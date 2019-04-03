make:
	@javac lib/*.java
	@mv lib/*.class .
	@javac Interface.java

clean:
	@rm *.class
