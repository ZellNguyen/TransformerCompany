package Models;

/**
 * Created by hoa_nguyen on 2017-09-08.
 */
public class Transformer implements Comparable<Transformer>{
    String name;
    TransformerSide side;
    int strength, intelligence, speed, endurance, rank, courage, firepower, skill;
    boolean isDestroyed;

    // Bosses
    private static final String OP = "Optimus Prime", PRE = "Predaking";

    public Transformer(String name, TransformerSide side, int strength, int intelligence, int speed, int endurance, int rank, int courage, int firepower, int skill) {
        this.name = name;
        this.side = side;

        // If any value is out of range between 1 and 10, throw exception
        if(strength > 10 || intelligence > 10 || speed > 10 || endurance > 10 || rank > 10 || courage > 10 || firepower > 10 || skill > 10
                || strength < 1 || intelligence < 1 || speed < 1 || endurance < 1 || rank < 1 || courage < 1 || firepower < 1 || skill < 1)
            throw new IllegalArgumentException("Invalid value");

        this.strength = strength;
        this.intelligence = intelligence;
        this.speed = speed;
        this.endurance = endurance;
        this.rank = rank;
        this.courage = courage;
        this.firepower = firepower;
        this.skill = skill;

        isDestroyed = false;
    }

    public BattleResult fightWith(Transformer opponent) {
        // Special Rules
        if( (this.name.equals(OP) || this.name.equals(PRE)) && (opponent.name.equals(OP) || opponent.name.equals(PRE)) )
            return BattleResult.END;

        if( this.name.equals(OP) || this.name.equals(PRE) ) {
            opponent.explode();
            return BattleResult.WIN;
        }

        if( opponent.name.equals(OP) || opponent.name.equals(PRE) ) {
            this.explode();
            return BattleResult.LOST;
        }

        // Basic Rules
        int diffCourage = this.courage - opponent.courage;
        int diffStrength = this.strength - opponent.strength;
        int diffSkill = this.skill - opponent.skill;
        int diffOverall = this.overallRating() - opponent.overallRating();

        // Lost
        if( (diffCourage <= -4 && diffStrength <= -3) || diffSkill <= -3 || diffOverall < 0) {
            this.explode();
            return BattleResult.LOST;
        }

        // Win
        else if ( (diffCourage >= 4 && diffStrength >= 3) || diffSkill >= 3 || diffOverall > 0) {
            opponent.explode();
            return BattleResult.WIN;
        }

        // Tie
        else {
            this.explode();
            opponent.explode();
            return BattleResult.TIE;
        }
    }

    public void explode() {
        this.isDestroyed = true;
    }

    public int overallRating() {
        return strength + intelligence + speed + endurance + firepower;
    }

    @Override
    public int compareTo(Transformer o) {
        return this.rank - o.rank;
    }

    /** Getters **/
    public String getName() {
        return name;
    }

    public TransformerSide getSide() {
        return side;
    }
}
