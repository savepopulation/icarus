# icarus
A lib to generate navigation classes in multi-module projects less boilerplate code and less error-prone.

### The problem
In multi-module projects, if you seperate your features as modules and don't follow single acitivty n fragment navigation architecture, you'll face-off with circular dependency problem. 
</br></br>Here's a [question](https://stackoverflow.com/questions/54037244/designing-modular-apps-circular-dependency-problem-in-navigation) is asked by me in Stackoverflow.
</br></br>After a research, I found [Mario's solution](https://github.com/sanogueralorenzo) for the problem and decided to use this in my current project.
</br>
It worked quite fine but there were two major points that needs to be focused on.</br>
</br>1- Too much boilerplate code we need to write for every feature in the project.
</br>2- It's hard to find the errors if someone repackage or rename the classes.
</br></br>
Than I decided to create a annotation processing library to focus on the points i mentioned above and reduce the boiler plate code and make the navigation architecture of the app less error-prone.
<br>
<br>
As I mentioned above and also highligtd in source code, **Icarus** is using the multi-module navigation approach that defined by **Mario Sanoguera de Lorenzo.** 

### Dependency<br>
```
maven { url 'https://jitpack.io' }
```
```
dependencies {
    implementation 'com.github.savepopulation:icarus:v1.0.0'
``` 
