# Death

<img src="https://slocraft.eu/slocraft-logo-512.png" width=124 height=124 align="right"/>

Death was developed for use on the [SloCraft](https://slocraft.eu) network.

Please keep in mind that the plugin has not been updated since May 2022.

### Description

Death is designed to handle death-related events in Minecraft servers. It includes functionality such as tracking player deaths, respawn locations, and a death compass. It also provides a set of commands for players to use to view death-related information.

### Features

- Per player death history
- Saving data to a local sqlite database
- Death compass
  - On every death, the player will get a compass pointing them to their death location.

### Dependencies

Death requires a PaperMc server version 1.18.2 or higher (not tested).

### Configuration

On startup a configuration file is loaded: `config.conf`. This file is generated automatically on first startup and can be found in the plugin's data folder.

- `config.conf` contains settings and language strings for the plugin.

### Usage

  - `/kjesemumrl` - Prints the players latest death location.
  - `/smrti` - Prints the players recent deaths.
  - `/death reload` - Reloads the plugin's configuration files. 

### Permissions

  - `death.admin` - Allows the player to use admin commands.
