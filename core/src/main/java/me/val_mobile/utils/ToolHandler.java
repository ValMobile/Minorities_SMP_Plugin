/*
    Copyright (C) 2025  Val_Mobile

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package me.val_mobile.utils;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ToolHandler {

    protected final Map<Material,Tool> toolMap = new HashMap<>();

    // TODO: Cache valid tool materials here
    protected final List<Material> pickaxes = new ArrayList<>();
    protected final List<Material> axes = new ArrayList<>();
    protected final List<Material> hoes = new ArrayList<>();
    protected final List<Material> shovels = new ArrayList<>();
    protected final List<Material> swords = new ArrayList<>();

    protected final List<Material> allTools = new ArrayList<>();
    protected final List<Material> instaBreakableByHand = new ArrayList<>();


    protected final List<Material> weapons = new ArrayList<>();

    public ToolHandler() {}

    public enum Tool {
        PICKAXE,
        SHOVEL,
        SHEARS,
        AXE,
        HOE,
        SWORD,
        NONE
    }

    /**
     * Gets the best tool type for a material
     * @param mat The block's material
     * @return Best tool type for that material
     */
    public Tool getBestToolType(Material mat) {
        Tool bestTool = toolMap.get(mat);
        if(bestTool == null) bestTool = Tool.NONE;
        return bestTool;
    }

}
