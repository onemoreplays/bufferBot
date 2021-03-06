# DiscordBotBase Project
## Create own Discord bot in Java with ease.

### Fast description
Hello. This repository hosts source files for Discord Bot Base. What is that?
Discord Bot Base is simply my self-made base for Discord Bot creation. Whole base is build upon
Javacord project with some features ;)


### Commands
First, my base now supports command arguments. That means you will be able to use
command line-like arguments ("-u, --users") for example.

Automatic loading done using Reflections, meaning you only have to create command class,
that's it. It will load automatically depending on config.

### Database
This base also has support of SQLite, meaning you can work with file like with classic SQL
but without any need for server. This is mostly used for local settings.

### Config
When we are talking about config, this base uses JSON config files. These configs are made
to be easy, variable and just working.
For your local development, copy `config.json` and rename it as `config.local.json`.
This config file is fully working, preferred and should be just copy of config.json, but without
api key.

### Communication
For communication with other servers across programming languages, this base uses JSON sockets.
To have your communication safe, I've implemented BouncyCastle to have safe Elliptic Curve Diffie Hellman (Curve25519) key exchange.

### Security
Code is being scanned on every push from internal Git repo. This repo is being mirrored to GitHub.
I've set up Code Scanning to have report about security, code coverage and more. Security is
my biggest concern so please, if you find anything wrong or funny, just report it. Thank you :)

### Want more info?
Right now, I'm working on documentation for this bot. It might just and simply take time.

### Support
If you would like to support my work, you can do so here: <https://commerce.coinbase.com/checkout/0d99044f-376d-4bd8-a343-cb7c161e8156>.
Thank you ♥
