# Hula-lang 

Hula is a simple scripting language for writing applications. 

The core language goals are:

* Readability - scripts should be readable without knowing the syntax
* Extensibility - it should be easy to plug in new functionality
* Flexibility - scripts can be modified without a build

Hula was designed for orchestrating underlying services. As such:

* Hula is good at modelling and gluing together services
* Hula is poor at capturing business logic - this should live in underlying services  

# Syntax


A Hula script looks like this:

```SQL
Set greeting="Hello, World!"
Echo $greeting
```


Hula scripts contain a set of commands which (almost) always follow this structure:


```SQL
Command Parameter1Name=Parameter1Value, Parameter2Name=Parameter2Value as ReturnVariable
```

In the above script: 

* The commands are **Set** and **Echo**.
* greeting is a parameter passed into the **Set** command, with value "Hello, World!"
* $greeting is a reference to a variable named greeting (created by the **Set** command). Variable references always have a leading dollar ($).


Our greeting script above could have read:

```SQL
NewMap greeting="Hello, World!" as greetingMap
Echo "Hula says $greetingMap.greeting"
```
 
In the above script:

* **NewMap** is a command which creates a Map object.
* greetingMap is the return variable from the **NewMap** command.
* The **Echo** command embeds a reference to the greetingMap variable, using Dot Notation to get the value of the greeting key (set by the **NewMap** command).

### Nested Blocks

Some commands support nested blocks, as shown below:

```SQL
Set names="Jeff,Jim,John"

# turns the CSV string into a list
Tokenise $names as nameList
Loop $nameList as name
   Echo "Hello, $name"
End
```

In the above script:

* The **Loop** command takes a list as a parameter.
* The nested block is executed for each element in the list.
* For each execution, the $name variable contains the next element in the list.

For further information please see the [Quick Start Guide].

# Getting Started

Hula-lang is packaged as an project which you can import directly into eclipse. 

Alternatively you can test Hula scripts using the batch/shell scripts supplied:

**Windows:**

	Hula Greetings
	
**Linux/Mac:**

	Hula.sh Greetings

Note: The test scripts live in test/scripts

# Latest Version

v0.1 alpha 1

# Contributing

* Please raise any issues in the github issue tracker

# Author

* [http://github.com/simoncurd]

# Copyright and License

[Apache 2 License]

Copyright 2013 Simon Curd simoncurd@gmail.com

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

[http://www.apache.org/licenses/LICENSE-2.0]

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
