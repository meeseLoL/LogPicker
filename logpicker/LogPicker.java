package com.runemate.meese.logpicker;

import com.runemate.game.api.hybrid.entities.*;
import com.runemate.game.api.hybrid.local.*;
import com.runemate.game.api.hybrid.local.hud.interfaces.*;
import com.runemate.game.api.hybrid.location.*;
import com.runemate.game.api.hybrid.location.navigation.cognizant.*;
import com.runemate.game.api.hybrid.region.*;
import com.runemate.game.api.hybrid.util.calculations.*;
import com.runemate.game.api.script.*;
import com.runemate.game.api.script.framework.*;
import com.runemate.game.api.script.framework.listeners.*;
import com.runemate.game.api.script.framework.listeners.events.*;
import com.runemate.ui.setting.annotation.open.*;
import org.apache.logging.log4j.*;
import com.runemate.game.api.hybrid.location.Coordinate;






public class LogPicker extends LoopingBot
{

    Coordinate upLumby = new Coordinate(3205, 3220, 2);


    @Override
    public void onStart(String... args)
    {
        if (upLumby.isReachable())
        {
            getLogger().info("Coordinate upLumby is reachable.");


        } else
        {
            getLogger().info("Coordinate upLumby is not reachable");
        }
    }


    public void onLoop()
    {
        if (Inventory.isFull())
        {
            if (!Bank.isOpen())
            {
                Bank.open();
                return; //wait tilll bank open
            }

            //deposit items
            Bank.depositInventory();
            //close
            Bank.close();
        }
        GroundItem logs = GroundItems.newQuery().names("Logs").results().nearest();

        if (logs != null && logs.isValid()) //check if found (logs)
        {
            boolean success = logs.take(); //try to take logs

            if (success)
            {
                getLogger().info("Grabbed logs");
            } else
            {
                getLogger().warn("No logs bruh");
            }


        } else
        {
            getLogger().info("no logs nearby. Hopping worlds");

            boolean hopSuccess = WorldHop.hopToRandom(WorldOverview::isMembersOnly);

            if (hopSuccess)
            {
                getLogger().info("Hopped to new world.");
            } else
            {
                getLogger().warn("failed to hop.");
            }
        }

    }



}