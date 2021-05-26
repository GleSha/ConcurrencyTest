call mvn clean install
call java -jar .\target\jcstress.jar -time 30000 -iters 10 -v -t org.sample.SingletonCreation