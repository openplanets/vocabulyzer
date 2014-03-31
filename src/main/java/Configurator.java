import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;
import java.io.IOException;
import java.util.Set;
import java.util.logging.ConsoleHandler;

/**
 * Created by artur on 3/31/14.
 */
public class Configurator {
    private Properties config;
    public Configurator(String pathToVocabulary){

        this.config = new Properties();
        try {
            this.config.load( Configurator.class.getClassLoader().getResourceAsStream( "vocabulary.mapping" ) );
        } catch ( final IOException e ) {
            e.printStackTrace();
        }

        final File f = new File( pathToVocabulary );

        if ( f.exists() && f.isFile() ) {
            FileInputStream stream = null;
            try {
                stream = new FileInputStream( f );
                this.config.load( stream );
            } catch ( final IOException e ) {
                e.printStackTrace();
            } finally {
                try {
                    if ( stream != null ) {
                        stream.close();
                    }
                } catch ( final IOException e ) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("User defined config file was not found.");
        }
    }
    Set<String> getPropertyNames(){
        return config.stringPropertyNames();
    }
    String getPropertyValueByName(String name){
        return config.getProperty(name);
    }
}
