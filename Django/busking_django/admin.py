from django.contrib import admin
from busking_django.models import Event

class EventAdmin(admin.ModelAdmin):
    list_display=('userID','showName','showTitle', 'showLocation', 'showGenre', 'showHeart', 'showTime', 'showDescription')

admin.site.register(Event, EventAdmin)
# Register your models here.
