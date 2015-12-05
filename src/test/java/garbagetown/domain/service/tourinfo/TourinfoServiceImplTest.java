package garbagetown.domain.service.tourinfo;

import garbagetown.domain.model.Arrival;
import garbagetown.domain.model.Departure;
import garbagetown.domain.model.Tourinfo;
import garbagetown.domain.repository.tourinfo.TourinfoCriteria;
import garbagetown.domain.repository.tourinfo.TourinfoRepository;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by garbagetown on 12/6/15.
 */
public class TourinfoServiceImplTest {

    TourinfoServiceImpl tourinfoService;
    TourinfoRepository tourinfoRepository;

    @Before
    public void setUp() {
        tourinfoService = new TourinfoServiceImpl();
        tourinfoRepository = mock(TourinfoRepository.class);
        tourinfoService.tourinfoRepository = tourinfoRepository;
    }

    @Test
    public void testSearch() {

        TourinfoCriteria criteria = new TourinfoCriteria();
        Pageable pageable = new PageRequest(0, 10);

        List<Tourinfo> list = new ArrayList<>();

        Tourinfo info = new Tourinfo();
        info.setTourCode("12345678");

        Arrival arrival = new Arrival();
        arrival.setArrCode("aaaa");
        info.setArrival(arrival);

        Departure departure = new Departure();
        departure.setDepCode("dddd");
        info.setDeparture(departure);

        list.add(info);

        Page<Tourinfo> page = new PageImpl<>(list);

        when(tourinfoRepository.findPageBySearchCriteria(criteria, pageable)).thenReturn(page);

        Page<Tourinfo> result = tourinfoService.search(criteria, pageable);

        assertThat(result, CoreMatchers.is(page));
    }
}
