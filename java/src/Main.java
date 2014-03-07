import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static final String URL = "http://pokemondb.net/pokedex/national";
    public static final String POKEMON_ELEMENT_CLASS = ".infocard-tall";
    public static final String POKEMON_NAME_CLASS = "ent-name";
    public static final String POKEMON_TYPE_CLASS = "itype";
    public static final String OUTPUT_FILE = "pokemon.json";

    public static void main(String[] args) throws Exception {
        Document doc = Jsoup.connect(URL).get();
        Elements pokemonElements = doc.select(POKEMON_ELEMENT_CLASS);
        List<Pokemon> pokemonList = new ArrayList<Pokemon>();
        for (Element element : pokemonElements) {
            Pokemon pokemon = new Pokemon();
            pokemon.name = element.getElementsByClass(POKEMON_NAME_CLASS).html();
            Elements types = element.getElementsByClass(POKEMON_TYPE_CLASS);
            for (Element type : types) {
                pokemon.types.add(type.html());
            }
            pokemonList.add(pokemon);
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(OUTPUT_FILE), pokemonList);
    }
}

class Pokemon {
    public String name;
    public List<String> types = new ArrayList<String>();
}