package com.bv.exercise.ActionMonitor.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bv.exercise.ActionMonitor.ActionMonitorApplication;
import com.bv.exercise.ActionMonitor.model.TimeSeries;
import com.bv.exercise.ActionMonitor.repository.TimeSeriesRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ActionMonitorApplication.class)
@TestPropertySource(properties = "spring.datasource.url=jdbc:h2:mem:dev")
public class TimeSeriesControllerTest {

    private static final String BASE_URL = "/timeseries/";
    private static final String NOT_EXISTING_ID = "notExistingId";
    private static final String ID = "abc-1";
    private static final Long TIME = 10L;
    private static final TimeSeries TIME_SERIES;

    static {
        TIME_SERIES = new TimeSeries();
        TIME_SERIES.setTime(TIME);
        TIME_SERIES.setId(ID);
    }

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private TimeSeriesRepository timeSeriesRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        timeSeriesRepository.save(TIME_SERIES);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @After
    public void tearDown() {
        timeSeriesRepository.deleteAll();
    }

    @Test
    public void tesGetNotExistingTimeSeriesData() throws Exception {
        mockMvc.perform(get(BASE_URL + NOT_EXISTING_ID))
            .andExpect(status().isNotFound());
    }

    @Test
    public void tesGetTimeSeriesData() throws Exception {
        mockMvc.perform(get(BASE_URL + ID))
            .andExpect(status().isOk())
            .andExpect(content().string(objectMapper.writeValueAsString(TIME_SERIES)));
    }

    @Test
    public void testInsertTimeSeriesData() throws Exception {
        final TimeSeries timeSeries = createTimeSeries("abc-2", 20L);
        final String expectedLocation = BASE_URL + "abc-2";

        final MvcResult mvcResult = mockMvc.perform(
            post(BASE_URL).content(objectMapper.writeValueAsString(timeSeries)).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andReturn();

        final String location = mvcResult.getResponse().getHeader("Location");
        assertThat("Location header should be relative!", location, is(expectedLocation));
        assertThat("There should be a plus time series object!", timeSeriesRepository.count(), is(2L));
    }

    @Test
    public void testInsertAgainTimeSeriesData() throws Exception {
        final TimeSeries timeSeries = createTimeSeries("abc-2", 20L);

        mockMvc.perform(
            post(BASE_URL).content(objectMapper.writeValueAsString(timeSeries)).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());
        mockMvc.perform(
            post(BASE_URL).content(objectMapper.writeValueAsString(timeSeries)).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isConflict());
    }

    @Test
    public void testDeleteTimeSeriesData() throws Exception {
        mockMvc.perform(delete(BASE_URL + ID)).andExpect(status().isNoContent());
        mockMvc.perform(delete(BASE_URL + ID)).andExpect(status().isNoContent());

        assertThat("The initial time series data should be deleted!", timeSeriesRepository.count(), is(0L));
    }

    @Test
    public void testUpdateExistingTimeSeriesData() throws Exception {
        final TimeSeries timeSeries = createTimeSeries(ID, 30L);

        mockMvc.perform(
            put(BASE_URL).content(objectMapper.writeValueAsString(timeSeries)).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
        mockMvc.perform(
            put(BASE_URL).content(objectMapper.writeValueAsString(timeSeries)).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        assertThat("The already existing time series data should be updated!", timeSeriesRepository.findOne(ID),
            is(timeSeries));
    }

    @Test
    public void testUpdateNotExistingTimeSeriesData() throws Exception {
        final TimeSeries timeSeries = createTimeSeries("abc-3", 40L);

        mockMvc.perform(
            put(BASE_URL).content(objectMapper.writeValueAsString(timeSeries)).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        assertNotNull("Not existing time series data should be saved into DB!", timeSeriesRepository.findOne("abc-3"));
    }

    private TimeSeries createTimeSeries(final String id, final Long time) {
        final TimeSeries timeSeries = new TimeSeries();
        timeSeries.setId(id);
        timeSeries.setTime(time);
        return timeSeries;
    }
}
