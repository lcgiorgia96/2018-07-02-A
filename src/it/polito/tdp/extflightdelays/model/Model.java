package it.polito.tdp.extflightdelays.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;

public class Model {

	Graph<Airport,DefaultWeightedEdge> grafo;
	List<Airport> aeroporti;
	ExtFlightDelaysDAO dao;
	List<Rotta> rotte;
	Map<Integer,Airport> idMap;
	List<Airport> connessi;
	List<Airport> best;
	int totMiglia;
	
	public Model () {
		dao = new ExtFlightDelaysDAO();
		
	}
	
	
	
	
	public void creaGrafo(int x) {
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		aeroporti = dao.getAeroporti(x);
		
		Graphs.addAllVertices(this.grafo, aeroporti);
		idMap = new HashMap<>();
		for (Airport a: this.grafo.vertexSet()) {
			idMap.put(a.getId(), a);
		}
		System.out.println(grafo.vertexSet().size());
		
		rotte = dao.getRotte(idMap);
		
		for (Rotta r: rotte) {
			Airport a1 = r.getA1();
			Airport a2 = r.getA2();
			int peso = r.getDistanza();
			DefaultWeightedEdge edge = grafo.getEdge(a1, a2);
			if (edge == null) {
			Graphs.addEdgeWithVertices(this.grafo, a1, a2, peso);
			} else {
				grafo.setEdgeWeight(edge, (grafo.getEdgeWeight(edge)+peso)/2);
			}
		}
		
		System.out.println(grafo.edgeSet().size());
	}

	public List<Airport> getAeroporti() {
		
		return this.aeroporti;
	}




	public List<Airport> getConnessi(Airport a) {
		connessi = Graphs.neighborListOf(this.grafo, a);
		List<Rotta> rotte =new ArrayList<>();
		List<Airport> finale = new ArrayList<>();
		for (Airport a1: connessi) {
			rotte.add(new Rotta(a,a1,(int) grafo.getEdgeWeight(grafo.getEdge(a, a1))));
		}
		Collections.sort(rotte);
		for (Rotta r: rotte) {
			finale.add(r.getA2());
		}
		return finale;
	}




	public List<Airport> getPercorso(Airport a, int miglia) {
		
		best = new LinkedList<>();
		
		List<Airport> parziale = new LinkedList<>();
		totMiglia =0;
		parziale.add(a);
		percorso(parziale,miglia);
		return best;
	}




	private void percorso(List<Airport> parziale, int miglia) {
		Airport ultimo = parziale.get(parziale.size()-1);
		List<Airport> candidati = Graphs.neighborListOf(this.grafo, ultimo);
		if (totMiglia >miglia) {
		return;	
		}
		for (Airport a: candidati) {
			if (!parziale.contains(a) && (totMiglia+grafo.getEdgeWeight(grafo.getEdge(ultimo, a))<miglia)) {
				parziale.add(a);
				totMiglia+=grafo.getEdgeWeight(grafo.getEdge(ultimo, a));
				this.percorso(parziale,miglia);
				parziale.remove(parziale.size()-1);
				
			}
		
		}
		
		if (parziale.size()>best.size()) {
			best = new LinkedList<>(parziale);
		}
		
		
		
	}




	public int getTotMiglia() {
		return totMiglia;
	}





}
