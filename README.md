# libCitizen
Personally I have always liked the styles of the npc like those that are in the Minecraft Java Edition servers, but in Minecraft Bedrock Edition it can be very complex to make them for plugin developers who are just starting out or most only decide to use line breaks. This library will help all developers to integrate npc with MC Java Edition styles to Bedrock Edition servers, besides being easier to integrate them in plugins or minigames.

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

