from django.contrib import admin
from .models import Content
from .models import Signup

# class ContentAdmin(admin.ModelAdmin):
#     list_display=('userID','showName','showTitle', 'showLocation', 'showGenre', 'showHeart', 'showTime', 'showDescription')
#
# class SignupAdmin(admin.ModelAdmin):
#     list_display=('userId', 'userPw', 'pwCheck', 'name', 'mail', 'phone')
#
# admin.site.register(Content, ContentAdmin)
# admin.site.register(Signup, SignupAdmin)

admin.site.register(Content)
admin.site.register(Signup)


# Register your models here.
