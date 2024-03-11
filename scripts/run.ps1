javac -cp ".;lib/*;src/mailtools/*;src/argtools/*" -d "out" src/*.java src/mailtools/*.java src/argtools/*.java
java -cp ".;lib/*;out;out.mailtools;out.argtools" App