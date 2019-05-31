package App.Model;

import java.util.ArrayList;

public class Pizza {
	public int idPizza;
	
	private String nomPizza;
	private ArrayList<String> listIngredients;
	private double prix;
	
	public Pizza(int idPizza, String nomPizza, ArrayList<String> listIngredients, double prix) {
		super();
		this.idPizza = idPizza;
		this.nomPizza = nomPizza;
		this.listIngredients = listIngredients;
		this.prix = prix;
	}
	
	public String getNomPizza() {
		return nomPizza;
	}
	public void setNomPizza(String nomPizza) {
		this.nomPizza = nomPizza;
	}
	public ArrayList<String> getListIngredients() {
		return listIngredients;
	}
	public void setListIngredients(ArrayList<String> listIngredients) {
		this.listIngredients = listIngredients;
	}
	public double getPrix() {
		return prix;
	}
	public void setPrix(double prix) {
		this.prix = prix;
	}

	@Override
	public String toString() {
		return "Pizza [idPizza=" + idPizza + ", nomPizza=" + nomPizza + ", listIngredients=" + listIngredients
				+ ", prix=" + prix + "]";
	}
}
