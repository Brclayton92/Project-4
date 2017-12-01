package ufl.cs1.controllers;

import game.controllers.DefenderController;
import game.models.Defender;
import game.models.Game;
import game.models.Node;
import game.models.*;

import java.util.List;

import java.util.ArrayList;

public final class StudentController implements DefenderController
{
	public int brocksGhostDefault(Game game, long timeDue, int i){ // method that can command a ghost to chase pacman
		int action = 0;
		List<Defender> enemies = game.getDefenders(); // List of defender objects
		Attacker pacMan = game.getAttacker(); // attacker object

		action = enemies.get(i).getNextDir(pacMan.getLocation(), true);
		return action;
	}

	public int brocksGhostDefault2(Game game, long timeDue, int i){ //method that can command a ghost to guard power pills and regular pills
		int action = -1;  // stores return value                    // if all power pills are consumed
		List<Defender> enemies = game.getDefenders(); // List of defender objects
		List<Node> pillLocations = game.getPillList(); //List of possible power pills on map
		List<Node> actualPowerPillLocations = game.getPowerPillList(); // list of actual power pills
		Maze currentMaze = game.getCurMaze(); //maze object
		List<Node> pills = currentMaze.getPillNodes(); // list of regular pills on map

		if (actualPowerPillLocations.size() != 0) { //if the map contains power pills, commands ghost to patrol near one
			action = enemies.get(i).getNextDir(actualPowerPillLocations.get(0), true);
		}

		/*else { //FIXME : old / inferior pill camping statement, delete before submission
			action = enemies.get(i).getNextDir(pillLocations.get(0), true);
		}*/

		else {                                      // if no power pills are on the map, commands ghost to patrol near regular pills
			action = enemies.get(i).getNextDir(pills.get(0), true);
		}

		return action;

	}

	public void init(Game game) { }

	public void shutdown(Game game) { }

	public int[] update(Game game,long timeDue)
	{
		int i = 0;
		int[] actions = new int[Game.NUM_DEFENDER];


		for (i = 0; i < 2; i++) { // commands 2 of 4 ghosts to chases Pacman
			actions[i] = brocksGhostDefault(game, timeDue, i);
		}
		for (i = 2; i < actions.length; i++) { // commands 2 of 4 ghosts to guard power pills then regular pills when all power pills are consumed
			actions[i] = brocksGhostDefault2(game, timeDue, i);
		}

		return actions;
	}
}