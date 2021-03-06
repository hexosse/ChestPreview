/*
 * Copyright 2016 hexosse
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.github.hexocraft.chestpreview.command;

import com.github.hexocraft.chestpreview.ChestPreviewApi;
import com.github.hexocraft.chestpreview.ChestPreviewPlugin;
import com.github.hexocraft.chestpreview.configuration.Permissions;
import com.github.hexocraftapi.command.Command;
import com.github.hexocraftapi.command.CommandArgument;
import com.github.hexocraftapi.command.CommandInfo;
import com.github.hexocraftapi.command.type.ArgTypeString;
import com.github.hexocraftapi.util.ChestUtil;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;

import java.util.Set;

/**
 * This file is part ChestPreview
 *
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class CpCommandRename extends Command<ChestPreviewPlugin>
{
    /**
     * @param plugin The plugin that this object belong to.
     */
    public CpCommandRename(ChestPreviewPlugin plugin)
    {
        super("rename", plugin);
        this.setAliases(Lists.newArrayList("r"));
        this.setDescription(StringUtils.join(plugin.messages.cRename,"\n"));
        this.setPermission(Permissions.ADMIN.toString());

        this.addArgument(new CommandArgument<String>("name", ArgTypeString.get(), true));
    }

    /**
     * Executes the given command, returning its success
     *
     * @param commandInfo Info about the command
     *
     * @return true if a valid command, otherwise false
     */
    @Override
    public boolean onCommand(CommandInfo commandInfo)
    {
        // Check player
        final Player player  = commandInfo.getPlayer();
        if(player==null) return false;

        // Check name
        String name = commandInfo.getNamedArg("name");
        if(name.isEmpty()) return false;

        // get the chest player is looking at
        Block block = player.getTargetBlock((Set<Material>) null, 10);
        if(block == null) return false;
        Chest chest = ChestUtil.getChest(block);

        // Check if the block is a chest preview
        if(chest == null) return false;
        if(!ChestPreviewApi.isChestPreview(chest)) return false;

        // Rename the chest
        ChestPreviewApi.rename(chest, ChatColor.translateAlternateColorCodes('&', name), commandInfo.getPlayer());

        return true;
    }
}
