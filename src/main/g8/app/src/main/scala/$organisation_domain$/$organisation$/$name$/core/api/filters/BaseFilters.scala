package $organisation_domain$.$organisation$.$name$.core.api.filters

import com.google.inject.Inject
import play.api.http.DefaultHttpFilters

class BaseFilters @Inject()(
    errorHandlingFilter: ErrorHandlingFilter
) extends DefaultHttpFilters(errorHandlingFilter)