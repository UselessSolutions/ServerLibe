# ServerLibe
A server side mod library for listening to and interracting with an event system

_________________________________________________________
Include in project with:
```gradle
implementation "com.github.UselessSolutions:<release-tag>"
```
_________________________________________________________
Or Include:
```gradle
	ivy {
		url = "https://github.com/UselessSolutions"
		patternLayout {
			artifact "[organisation]/releases/download/[revision]/[module]-[revision].jar"
			m2compatible = true
		}
		metadataSources { artifact() }
	}
```
This ^ in under repositories with:
```gradle
implementation "serverlibe:serverlibe:${project.serverlibe_version}"
```
This ^ as the implementation.
_________________________________________________________
