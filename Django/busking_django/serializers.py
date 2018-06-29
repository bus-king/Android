from rest_framework import serializers
from busking_django.models import Content
from busking_django.models import Signup

class ContentSerializer(serializers.ModelSerializer):
    # ModelSerializer 를 이용해서 아래와 같이 짧은 코드로 직렬화 필드를 정의할 수 있다
    class Meta:
        model = Content
        # fields = ('id','title','author','pw','content')
        fields = ('id', 'userID', 'showName', 'showTitle', 'showLocation', 'showGenre', 'showHeart', 'showTime', 'showDescription')

    # 신규 Bbs instance를 생성해서 리턴해준다
    def create(self, validated_data):
        return Content.objects.create(**validated_data)

    # 생성되어 있는 Bbs instance 를 저장한 후 리턴해준다
    def update(self, instance, validated_data):
        instance.userID = validated_data.get('userID', instance.userID)
        instance.showName = validated_data.get('showName', instance.showName)
        instance.showTitle = validated_data.get('showTitle', instance.showTitle)
        instance.showLocation = validated_data.get('showLocation', instance.showLocation)
        instance.showGenre = validated_data.get('showGenre', instance.showGenre)
        instance.showHeart = validated_data.get('showHeart', instance.showHeart)
        instance.showTime = validated_data.get('showTime', instance.showTime)
        instance.showDescription = validated_data.get('showDescription', instance.showDescription)
        instance.save()
        return instance

class SignupSerializer(serializers.ModelSerializer):
    # ModelSerializer 를 이용해서 아래와 같이 짧은 코드로 직렬화 필드를 정의할 수 있다
    class Meta:
        model = Signup
        # fields = ('id','title','author','pw','content')
        fields = ('userId', 'userPw', 'pwCheck', 'name', 'mail', 'phone')

    # 신규 Bbs instance를 생성해서 리턴해준다
    def create(self, validated_data):
        return Signup.objects.create(**validated_data)

    # 생성되어 있는 Bbs instance 를 저장한 후 리턴해준다
    def update(self, instance, validated_data):
        instance.userId = validated_data.get('userId', instance.userId)
        instance.userPw = validated_data.get('userPw', instance.userPw)
        instance.pwCheck = validated_data.get('pwCheck', instance.pwCheck)
        instance.name = validated_data.get('name', instance.name)
        instance.mail = validated_data.get('mail', instance.mail)
        instance.phone = validated_data.get('phone', instance.phone)
        instance.save()
        return instance
