from django.conf.urls import url
from rest_framework.urlpatterns import format_suffix_patterns  
from busking_django import views

urlpatterns = [
    url(r'^$', views.event_list),
    url(r'^(?P<pk>[0-9]+)/$', views.event_detail),
]

urlpatterns = format_suffix_patterns(urlpatterns)
