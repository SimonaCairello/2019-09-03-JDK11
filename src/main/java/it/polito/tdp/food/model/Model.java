package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	
	private Graph<String, DefaultWeightedEdge> graph;
	private FoodDao dao;
	
	private List<String> bestCammino;
	private Integer pesoMax;
	
	public Model() {
		this.dao = new FoodDao();
		this.pesoMax = 0;
	}

	public void generateGraph(Integer calorie) {
		this.graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(this.graph, this.getDistinctPortions(calorie));
		
		List<Adiacenza> adiacenze = this.dao.getAdiacenze(this.graph.vertexSet());
		for(Adiacenza a : adiacenze) {
			Graphs.addEdge(this.graph, a.getPortion1(), a.getPortion2(), a.getPeso());
		}
	}
	
	public List<String> getDistinctPortions(Integer calorie) {
		return this.dao.getDistinctPortions(calorie);
	}
	
	public Integer getNumVertici() {
		return this.graph.vertexSet().size();
	}
	
	public Integer getNumArchi() {
		return this.graph.edgeSet().size();
	}
	
	public List<PorzionePeso> getCorrelati(String porzione) {
		List<PorzionePeso> correlati = new ArrayList<>();
		
		for(String v : Graphs.neighborListOf(this.graph, porzione)) {
			correlati.add(new PorzionePeso(v, (int) this.graph.getEdgeWeight(this.graph.getEdge(v, porzione))));				
		}
		return correlati;
	}
	
	public List<String> getCamminoMax(Integer passi, String porzione) {
		List<String> parziale = new ArrayList<>();
		parziale.add(porzione);
		this.ricorsiva(parziale, passi, 0);
		return this.bestCammino;
	}
	
	public Integer getPesoCamminoMax() {
		return this.pesoMax;
	}
	
	public void ricorsiva(List<String> parziale, Integer passi, Integer peso) {
		if(parziale.size()-1==passi) {
			if(this.pesoMax<peso) {
				this.bestCammino = new ArrayList<>(parziale);
				this.pesoMax = peso;
				return;
			}
			return;
		}
		
		for(String p : Graphs.neighborListOf(this.graph, parziale.get(parziale.size()-1))) {
			if(!parziale.contains(p)) {
				Integer peso2 = peso + (int) this.graph.getEdgeWeight(this.graph.getEdge(p, parziale.get(parziale.size()-1)));
				parziale.add(p);
				this.ricorsiva(parziale, passi, peso2);
				
				parziale.remove(parziale.size()-1);
			}
		}
		return;
	}
}
