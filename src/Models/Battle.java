package Models;

import Models.BattleResult;
import Models.Team;
import Models.Transformer;
import Models.TransformerSide;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by hoa_nguyen on 2017-09-08.
 */
public class Battle {
    Team autobot = Team.AUTOBOT, deception = Team.DECEPTION;
    public boolean didFinish = false, isAllDestroyed = false;

    public Battle() {
        this.readCSV("battle.csv");
        autobot.sort();
        deception.sort();
    }

    public int numberOfBattles() {
        return Math.min(autobot.size(), deception.size());
    }

    public void start() {
        BattleResult result;

        // Start 1 vs. 1 battle
        for(int i = 0; i < this.numberOfBattles(); i++) {
            result = autobot.get(i).fightWith(deception.get(i));

            switch (result) {
                case WIN:
                    autobot.score();
                    break;
                case LOST:
                    deception.score();
                    break;
                case TIE:
                    autobot.score();
                    deception.score();
                    break;
                case END:
                    isAllDestroyed = true;
                    break;
            }

            if(isAllDestroyed)
                break;
        }

        // The whole battle ends and explodes
        if(isAllDestroyed) {
            for (Transformer tf : autobot.members)
                tf.explode();
            for (Transformer tf : deception.members)
                tf.explode();
        }

        // Claim finish
        didFinish = true;
    }

    public Team getWinner() {
        if(!didFinish)
            throw new IllegalStateException("Models.Battle hasn't started yet");

        if(autobot.score > deception.score) return autobot;
        if(autobot.score < deception.score) return deception;

        return null;
    }

    public ArrayList<Transformer> getSurvivors() {
        if(!didFinish)
            throw new IllegalStateException("Models.Battle hasn't started yet");

        if(isAllDestroyed) return new ArrayList<>();

        if(getWinner().side == TransformerSide.AUTOBOT)
            return deception.getSurvivors();
        if(getWinner().side == TransformerSide.DECEPTICON)
            return autobot.getSurvivors();

        return null;
    }

    private void readCSV(String fileName) {
        String csvFile = fileName;
        BufferedReader br = null;
        String line;
        String csvSplitBy = ",";
        try {
            br = new BufferedReader(new FileReader(csvFile));
            // Skip the first line
            br.readLine();

            while ((line = br.readLine()) != null) {
                //use comma as separator
                String[] args = line.split(csvSplitBy);

                String name = args[0];
                TransformerSide side = null;
                switch (args[1]) {
                    case "A":
                        side = TransformerSide.AUTOBOT;
                        break;
                    case "D":
                        side = TransformerSide.DECEPTICON;
                        break;
                }
                int strength = Integer.valueOf(args[2]);
                int intelligence = Integer.valueOf(args[3]);
                int speed = Integer.valueOf(args[4]);
                int endurance = Integer.valueOf(args[5]);
                int rank = Integer.valueOf(args[6]);
                int courage = Integer.valueOf(args[7]);
                int firepower = Integer.valueOf(args[8]);
                int skill = Integer.valueOf(args[9]);

                Transformer tf = new Transformer(name, side, strength, intelligence, speed, endurance, rank, courage, firepower, skill);

                if(tf.getSide() == TransformerSide.AUTOBOT)
                    autobot.addMember(tf);
                else
                    deception.addMember(tf);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } finally
            {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
