package edu.adam.gameapp;

public class HiScore
{
    int idscore;
    String gdate;
    String pName;
    int score;


    public HiScore(int idscore, String gdate, String pName, int score)
    {
        this.idscore = idscore;
        this.gdate = gdate;
        this.pName=pName;
        this.score = score;
    }
    public HiScore()
    {

    }
    public HiScore(String gdate, String pName, int score)
    {
        this.gdate = gdate;
        this.pName = pName;
        this.score = score;
    }
    public int getIdScore(){return idscore;}
    public void setIdscore(int idscore){this.idscore=idscore;}
    public String getGdate(){return gdate;}
    public void setGdate(String gdate){this.gdate=gdate;}
    public String getpName(){return pName;}
    public void setpName(String pName){this.pName = pName;}
    public int getScore(){return score;}
    public void setScore(int score){this.score=score;}

}
