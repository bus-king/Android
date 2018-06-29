from django.conf.urls import url
from rest_framework.urlpatterns import format_suffix_patterns
from busking_django import views

urlpatterns = [
    url(r'^signUp$', views.event_signup),
    url(r'^createContents$', views.event_contents),
    url(r'^(?P<pk>[0-9]+)/$', views.event_detail),
]

urlpatterns = format_suffix_patterns(urlpatterns)
