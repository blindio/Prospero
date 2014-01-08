/*******************************************************************************
 * Copyright 2014 S. Thorson Little
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package io.github.blindio.prospero.core.elementgroup;

public interface ElementGroup {

    /**
     * Returns the id prefix for this ElementGroup.<br/>
     * This id is prepended to the suffix of an element in the group to build
     * the full index. <br/>
     * For example if the ElementGroup Id is 'prospero.joblist.0' and the
     * element's id suffix is 'detaillink', the full id for this element is
     * 'prospero.joblist.0.detaillink'.
     * 
     * @return
     */
    public String getId();

    /**
     * Returns the id for an element in this element group. Does this by
     * prepending the ElementGroup's id to the elementIdSuffix. <br/>
     * For example if the ElementGroup Id is 'prospero.joblist.0' and the
     * element's id suffix is 'detaillink', the full id for this element is
     * 'prospero.joblist.0.detaillink'.
     * 
     * @param elementIdSuffix
     * @return
     */
    public String buildElementId(String elementIdSuffix);

    /**
     * Test to determine whether this ElementGroup is present
     * 
     * @return
     */
    public boolean isPresent();

}
