library(dplyr)
library(ggplot2)
library(sf)
library(rnaturalearth)

ramka<-read.csv("ED.csv")

colnames(ramka)[4]<-"ed"
max(ramka$Year)->maxYear

ramka %>% 
  filter(Year==maxYear)->filtered1

filtered1 %>% 
  select(Entity, Code)

world <- ne_countries(scale = "small",returnclass = "sf")
world2<- filter(world, world$name!="Antarctica")

ggplot()+
  geom_sf(data=world2)+
  theme_void()+
  theme(panel.grid = element_blank(),
        plot.background = element_rect(fill="#d5d4bb"))->baseMap

filtered1$Code <- ifelse(filtered1$Entity == "France", "-99", filtered1$Code)
filtered1$Code <- ifelse(filtered1$Entity == "Norway", "NOR", filtered1$Code)
filtered1$Code <- ifelse(filtered1$Entity == "Cyprus", "CYPR", filtered1$Code)
filtered1$Code <- ifelse(filtered1$Entity == "Somaliland", "SOMALILAND", filtered1$Code)
filtered1$Code <- ifelse(filtered1$Entity == "Kosovo", "KOSOVO", filtered1$Code)
world$iso_a3<- ifelse(world$name=="Norway", "NOR", world$iso_a3)
world$iso_a3<- ifelse(world$name=="N. Cyprus", "CYPR", world$iso_a3)
world$iso_a3<- ifelse(world$name=="Somaliland", "SOMALILAND", world$iso_a3)
world$iso_a3<- ifelse(world$name=="Kosovo", "KOSOVO", world$iso_a3)

duplicate_iso_codes <- world %>%
  group_by(iso_a3) %>%
  filter(n() > 1) %>%
  pull(iso_a3) %>%
  unique()

world %>% 
  filter(iso_a3=="-99") %>% 
  select(name,iso_a3)

right_join(world,filtered1,by=c("iso_a3"="Code"))->joined1

color_pal<- colorRampPalette(c("#618b91", "#c7870b"))(100)

baseMap +
  geom_sf(data= joined1, aes(fill=log(ed)))+
  
  scale_fill_gradientn(colors=color_pal,
                       na.value ="darkgrey",
                       labels=c("0.001%","0.01%","0.1%","1.0%"))+
  labs(fill ="ED")+ 
  guides(fill=guide_colorbar(label.theme = element_text(color="navy",size=7), barheight = 0.5, barwidth =7))+
  theme(legend.position = "bottom",
        legend.title = element_text(color="navy", size =9, margin = margin(t = -10, unit = "pt")))->plot1

plot1






