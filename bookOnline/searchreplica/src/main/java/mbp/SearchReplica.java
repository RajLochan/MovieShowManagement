package mbp;

import lombok.extern.slf4j.Slf4j;
import mbp.Entity.MovieTheaterViewEntity;
import mbp.domainobject.SearchFilterRequest;
import mbp.search.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@SpringBootApplication
@Slf4j
@EnableAutoConfiguration
@ComponentScan(basePackages = {"mbp.*"})
public class SearchReplica implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(SearchReplica.class, args);
    }

    @Autowired
    SearchService searchService;
    @Override
    public void run(String... args) throws Exception {
        SearchFilterRequest request=new SearchFilterRequest();
        request.setShowDate(getDate());
        request.setCityName("Bengaluru");
        List<MovieTheaterViewEntity> data = searchService.searchRunningShows(request);
        System.out.println(data.toString());
      //  request.setMovieName("Pathaan");
        for(int i=0;i<10;i++) {
             data = searchService.theatreSearch(request);
            System.out.println(i +" <<th count response>>"+ data.toString());
        }
    }
    private static Date getDate(){
        Calendar calendar = Calendar.getInstance();

        // Set the month to October (0-based, so 9 represents October)
        calendar.set(Calendar.MONTH, Calendar.OCTOBER);

        // Set the day of the month to 1
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        // Get the Date object representing October 1st of the current year
        Date date = calendar.getTime();
        return date;
    }
}