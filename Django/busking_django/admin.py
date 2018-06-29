from django.contrib import admin
from busking_django.models import Event

class EventAdmin(admin.ModelAdmin):
    list_display=('title','author','created',)

admin.site.register(Event, EventAdmin)
# Register your models here.
