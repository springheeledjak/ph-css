/**
 * Copyright (C) 2006-2014 phloc systems (www.phloc.com)
 * Copyright (C) 2014 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.helger.css.property.customizer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.helger.commons.annotations.Nonempty;
import com.helger.css.property.ECSSProperty;
import com.helger.css.property.ICSSProperty;
import com.helger.css.propertyvalue.CSSValueMultiProperty;
import com.helger.css.propertyvalue.ICSSValue;

/**
 * Special customizer for the "border-bottom-left-radius" property.
 * 
 * @author Philip Helger
 */
@Immutable
public class CSSPropertyCustomizerBorderBottomLeftRadius extends AbstractCSSPropertyCustomizer
{
  @Nullable
  public ICSSValue createSpecialValue (@Nonnull final ICSSProperty aProperty,
                                       @Nonnull @Nonempty final String sValue,
                                       final boolean bIsImportant)
  {
    return new CSSValueMultiProperty (aProperty.getProp (),
                                      new ICSSProperty [] { aProperty,
                                                           aProperty.getClone (ECSSProperty._MOZ_BORDER_RADIUS_BOTTOMLEFT),
                                                           aProperty.getClone (ECSSProperty._WEBKIT_BORDER_BOTTOM_LEFT_RADIUS),
                                                           aProperty.getClone (ECSSProperty._KHTML_BORDER_BOTTOM_LEFT_RADIUS) },
                                      sValue,
                                      bIsImportant);
  }
}