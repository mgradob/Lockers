from django.conf.urls import patterns, include, url
from rest_framework.routers import DefaultRouter
from Lockers import views
from django.contrib import admin
admin.autodiscover()

router = DefaultRouter()
router.register(r'Users', views.UsersViewSet)
router.register(r'Rates', views.RatesViewSet)
router.register(r'Lockers', views.LockersViewSet)
router.register(r'Log', views.LogViewSet)
router.register(r'Areas', views.AreasViewSet)

urlpatterns = patterns(
    '',
    url(r'^', include(router.urls)),
    url(r'^api-auth/', include('rest_framework.urls', namespace='rest_framework')),
    url(r'^Lockers_Search/', views.LockersSearch.as_view(model='Lockers')),
    #url(r'^admin/', include(admin.site.urls)),
)
