ramka<- read.csv("ED.csv")
library(dplyr)
library(ggplot2)

colnames(ramka)[4]<-"ed"
ramka %>%
  filter(Entity=="Australia") %>%
  select(Year,ed ) ->dane

selectedYears<- c(1990,2000,2010,2019)
dane %>% 
  filter(Year %in% selectedYears)->danePoint

ggplot(dane, aes(x=Year, y=ed))+
  geom_line(size=1, color="#004851")+
  geom_point(data = danePoint, size=2, color="#c7870b")+
  scale_y_continuous(labels=scales::percent_format(scale=1), limits= c(0,1.1), expand=c(0,0,0,0.1))+
  scale_x_continuous(limits=c(1990,2022), breaks=c(1990,2000,2010,2019))+
  geom_text(data=danePoint, aes(label=paste(round(ed, digits=2),"%")),
            size=3.4, vjust=1.3,hjust=-0.1)+
  labs(x="Year", y="ED",
       title="% of people with eating disorders in Australia")+
  theme_minimal()+
  theme(plot.background = element_rect(fill="#d5d4bb"),
        axis.title.x= element_text(size=11.5),
        axis.title.y = element_text(size=10.5),
        plot.title = element_text(size=10.5, hjust = 0.5),
        panel.grid.major=element_line(color="grey2"),
        panel.grid.minor=element_blank(),
        axis.text.x = element_text(size=9))

  