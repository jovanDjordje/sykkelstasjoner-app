package com.bysykkelapp;

class Station {
    private final int id;
    private final String name;
    private final int capacity;
    private int numOfBikesAvailable;
    private int numOfDocsAvailable;

    Station(int id, String name, int cap){
        this.id = id;
        this.capacity = cap;
        this.name = name;
        // -1 value is default value, represents the case when the value is not assigned by
        // the API. In case the station id is not found.
        this.numOfBikesAvailable = -1;
        this.numOfDocsAvailable = -1;

    }
    public void setBikesAndDocs(int numBikes, int numDocs){
        this.numOfBikesAvailable = numBikes;
        this.numOfDocsAvailable  = numDocs;
    }
    public int getId(){
        return this.id;
    }

    @Override
    public
    String toString(){
        return    String.format("%10d%32s%15d%28d%27d", id, name, capacity, numOfBikesAvailable, numOfDocsAvailable);//id +": " + name + ": " + capacity + " " + numOfBikesAvailable + " " + numOfDocsAvailable;
    }
}
