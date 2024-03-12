# If windows wasn't so darn quirky I would only need one run script
javac -cp ".;lib/*;src/mailtools/*;src/argtools/*" -d "out" src/*.java src/mailtools/*.java src/argtools/*.java
java -cp ".;lib/*;out;out.mailtools;out.argtools" App --login