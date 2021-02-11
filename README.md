# libCitizen
Create MC Java Edition server-style NPCs in just a few lines of code.

##### Dependencies:

```xml
    <dependencies>
        <dependency>
            <groupId>org.citizen</groupId>
            <artifactId>libCitizen</artifactId>
            <version>1.0-SNAPSHOT</version>
            <scope>system</scope>
            <systemPath>${basedir}/lib/libCitizen.jar</systemPath>
        </dependency>
     </dependencies>
```

### Setup Example

```java
import cn.nukkit.plugin.PluginBase;
import org.citizen.CitizenLibrary;

public class Loader extends PluginBase {
    private CitizenLibrary library;

    @Override
    public void onEnable() {
        library = new CitizenLibrary(this);
    }
}
```

### Building a Citizen

```java
Citizen citizen = new Citizen();
citizen.setPosition(new Position(11, 4, 11, getServer().getDefaultLevel()));
citizen.setYaw(20);
citizen.setPitch(0);
citizen.setSkin(CitizenSkin.from(new File(getDataFolder() + "/skins/").toPath().resolve("mySKin.png")));
```

