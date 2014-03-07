import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        Document typesDocument = Jsoup.connect("http://www.pokeffectiveness.com/").get();
        List<Pokemon> pokemonList = new ArrayList<Pokemon>();

        for (Element pokemonElement : typesDocument.select(".poke_row")) {
            Pokemon pokemon = new Pokemon();
            pokemon.name = pokemonElement.getElementsByClass("poke_name").get(0).html();
            pokemon.pokedexNo = pokemonElement.getElementsByClass("pokedex_number").get(0).html();
            for (Element pokemonTypeElement : pokemonElement.getElementsByClass("poke_type")) {
                pokemon.types.add(pokemonTypeElement.html());
            }
            addDamageMods(pokemonElement, pokemon, "superweak", 400);
            addDamageMods(pokemonElement, pokemon, "weak", 200);
            addDamageMods(pokemonElement, pokemon, "normal_col", 100);
            addDamageMods(pokemonElement, pokemon, "resistant", 50);
            addDamageMods(pokemonElement, pokemon, "superresistant", 25);
            addDamageMods(pokemonElement, pokemon, "immune", 0);
            pokemonList.add(pokemon);
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("pokemon.json"), pokemonList);

    }

    private static void addDamageMods(Element pokemonElements, Pokemon pokemon, String category, int modifier) {
        for (Element pokemonElement : pokemonElements.getElementsByClass(category)) {
            for (Element typeElement : pokemonElement.getElementsByClass("type")) {
                pokemon.typeDamageMods.put(typeElement.html(), modifier);
            }
        }
    }
}

class Type {
    public List<String> types = new ArrayList<String>();
    public Map<String, Integer> typeDamageMods = new HashMap<String, Integer>();

    public Type(List<String> types, Map<String, Integer> typeDamageMods) {
        this.types = types;
        this.typeDamageMods = typeDamageMods;
    }
}

class Pokemon {
    public String pokedexNo;
    public String name;
    public List<String> types = new ArrayList<String>();
    public Map<String, Integer> typeDamageMods = new HashMap<String, Integer>();
}