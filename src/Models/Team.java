package Models;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by hoa_nguyen on 2017-09-08.
 */
public class Team {
    public TransformerSide side;
    public ArrayList<Transformer> members;
    public int score; // number of opponents eliminated

    // I create 2 singletons so that no other team can be created
    public static Team AUTOBOT = new Team(TransformerSide.AUTOBOT), DECEPTION = new Team(TransformerSide.DECEPTICON);

    private Team(TransformerSide side) {
        this.side = side;
        this.members = new ArrayList<>();
        this.score = 0;
    }

    public void addMember(Transformer member){
        if(member.side == this.side)
            this.members.add(member);
    }

    public void score() {
        this.score++;
    }

    public int size() {
        return members.size();
    }

    public void sort() {
        Collections.sort(members);
    }

    public Transformer get(int index) {
        return members.get(index);
    }

    public ArrayList<Transformer> getSurvivors() {
        ArrayList<Transformer> survivors = new ArrayList<>();
        for(Transformer tf : members)
            if (!tf.isDestroyed)  survivors.add(tf);

        return survivors;
    }

    @Override
    public String toString() {
        return this.side.toString();
    }
}
