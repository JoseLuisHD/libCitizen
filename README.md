# libCitizen
Personally I have always liked the styles of the npc like those that are in the Minecraft Java Edition servers, but in Minecraft Bedrock Edition it can be very complex to make them for plugin developers who are just starting out or most only decide to use line breaks. This library will help all developers to integrate npc with MC Java Edition styles to Bedrock Edition servers, besides being easier to integrate them in plugins or minigames.

![Picture](https://github.com/JoseLuisHD/libCitizen/blob/main/img/example.png)

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

### Controllers
A citizen requires help from some controllers to function properly. Currently the library adds these controllers automatically if you add the Citizen to the factory.
```java
library.getFactory().add(citizen);
```
If you are an experienced developer or want to create your own controllers, you should extend the class to Controllers

```java
import org.citizen.controller.Controllers;

public class MyCustomControllers extends Controllers {
    public MyCustomControllers(CitizenLibrary library) {
        super(library);
    }
}
```
Controller class example
```java
public class MyCustomControllers extends Controllers {
    public MyCustomControllers(CitizenLibrary library) {
        super(library);
    }

    @Override
    public void handle(DataPacketReceiveEvent event) {
        DataPacket packet = event.getPacket();
        Player player = event.getPlayer();

        if (packet instanceof InventoryTransactionPacket && ((InventoryTransactionPacket) packet).transactionType == InventoryTransactionPacket.TYPE_USE_ITEM_ON_ENTITY) {

            TransactionData data = ((InventoryTransactionPacket) packet).transactionData;

            if (data instanceof UseItemOnEntityData) {
                Citizen citizen = getLibrary().getFactory().getCitizen(((UseItemOnEntityData) data).entityRuntimeId);

                if (citizen == null) return;

                citizen.callInvoke(player);
            }
        }
    }

    @Override
    public void handle(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        getLibrary().getFactory().getCitizens().forEach((id, citizen) -> {
            if (citizen.getPosition().getLevel().getFolderName().equals(player.getLevel().getFolderName()))
                citizen.spawn(player);
        });
    }

    @Override
    public void handle(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        getLibrary().getFactory().getCitizens().forEach((id, citizen) -> citizen.despawn(player));
    }

    @Override
    public void handle(EntityLevelChangeEvent event) {
        Entity entity = event.getEntity();

        if (!(entity instanceof Player))
            return;

        Player player = ((Player) entity);
        Level origin = event.getOrigin();
        Level target = event.getTarget();

        getLibrary().getFactory().getCitizens().forEach((id, citizen) -> {
            String citizenLevelName = citizen.getPosition().getLevel().getFolderName();
            if (citizenLevelName.equals(origin.getFolderName()))
                citizen.despawn(player);

            if (citizenLevelName.equals(target.getFolderName()))
                citizen.spawn(player);
        });
    }
}
```

If you create your own controllers, the initialization of the library will be different.
```java
import cn.nukkit.plugin.PluginBase;
import org.citizen.CitizenLibrary;

public class Loader extends PluginBase {
    private CitizenLibrary library;

    @Override
    public void onEnable() {
        library = new CitizenLibrary(this, new MyCustomControllers(library));
    }
}
```
