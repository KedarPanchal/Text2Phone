javac -cp ".;lib/*;src/mailtools/*" -d "out" src/*.java src/mailtools/*.java
java -cp ".;lib/*;out;out.mailtools" App