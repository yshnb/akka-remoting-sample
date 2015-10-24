# akka-remoting-sample

## how to run

you can execute below command if run remoting-sample app.
```
sbt run
```

and then, ...

```
AkkaClientActor is starting...
AkkaServerActor(serverActor1) is starting...
AkkaServerActor(serverActor2) is starting...
AkkaClientActor get message from deadLetters: hello AkkaClientActor!
AkkaServerActor(serverActor2) get message from clientActor: hello AkkaServerActor
AkkaServerActor(serverActor1) get message from clientActor: hello AkkaServerActor
AkkaClientActor get shutdown message from serverActor2
AkkaClientActor get shutdown message from serverActor1
AkkaServerActor(serverActor1) get shutdown message from clientActor
AkkaServerActor(serverActor1) get shutdown message from clientActor
AkkaServerActor(serverActor2) get shutdown message from clientActor
AkkaServerActor(serverActor2) get shutdown message from clientActor
AkkaClientActor has been stopped.
AkkaServerActor(serverActor1) has been stopped.
AkkaServerActor(serverActor2) has been stopped.
```
