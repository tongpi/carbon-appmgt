/*
 *
 *  * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *  *
 *  * WSO2 Inc. licenses this file to you under the Apache License,
 *  * Version 2.0 (the "License"); you may not use this file except
 *  * in compliance with the License.
 *  * you may obtain a copy of the License at
 *  *
 *  *   http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing,
 *  * software distributed under the License is distributed on an
 *  * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  * KIND, either express or implied.  See the License for the
 *  * specific language governing permissions and limitations
 *  * under the License.
 *
 */

package org.wso2.carbon.appmgt.rest.api.store.utils.mappings;


import org.wso2.carbon.appmgt.api.model.Documentation;
import org.wso2.carbon.appmgt.api.model.DocumentationType;
import org.wso2.carbon.appmgt.rest.api.store.dto.DocumentDTO;
import org.wso2.carbon.appmgt.rest.api.store.dto.DocumentListDTO;
import org.wso2.carbon.appmgt.rest.api.util.exception.BadRequestException;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for mapping APPM core documentation related objects into REST API documentation
 * related DTOs
 */
public class DocumentationMappingUtil {
    /**
     * Converts a APIM core Document object into corresponding REST API Document DTO object
     *
     * @param documentation Documentation object
     * @return a new DocumentDTO object corresponding to given Documentation object
     */
    public static DocumentDTO fromDocumentationToDTO(Documentation documentation) {
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setDocumentId(documentation.getId());
        documentDTO.setName(documentation.getName());
        documentDTO.setSummary(documentation.getSummary());
        documentDTO.setType(DocumentDTO.TypeEnum.valueOf(documentation.getType().toString()));
        documentDTO.setOtherTypeName(documentation.getOtherTypeName());
        if (documentation.getSourceType() != null)
            documentDTO.setSourceType(DocumentDTO.SourceTypeEnum.valueOf(documentation.getSourceType().toString()));
        documentDTO.setSourceUrl(documentation.getSourceUrl());
        return documentDTO;
    }

    /**
     * Converts a REST API Document DTO object into corresponding APIM core Document object
     *
     * @param documentDTO DocumentDTO object
     * @return a new Documentation object corresponding to given DocumentDTO object
     */
    public static Documentation fromDTOtoDocumentation(DocumentDTO documentDTO) {
        if (documentDTO.getType() == null)
            throw new BadRequestException();
        Documentation documentation = new Documentation(DocumentationType.valueOf(documentDTO.getType().toString()),
                documentDTO.getName());
        documentation.setSummary(documentDTO.getSummary());
        if (documentDTO.getSourceType() != null)
            documentation
                    .setSourceType(Documentation.DocumentSourceType.valueOf(documentDTO.getSourceType().toString()));
        documentation.setSourceUrl(documentDTO.getSourceUrl());
        documentation.setOtherTypeName(documentDTO.getOtherTypeName());

        return documentation;
    }

    /**
     * Converts a List object of Documents into a DTO
     *
     * @param documentations List of Documentations
     * @param limit          maximum number of APIs returns
     * @param offset         starting index
     * @return DocumentListDTO object containing Document DTOs
     */
    public static DocumentListDTO fromDocumentationListToDTO(List<Documentation> documentations, int offset,
                                                             int limit) {
        DocumentListDTO documentListDTO = new DocumentListDTO();
        List<DocumentDTO> documentDTOs = documentListDTO.getList();
        if (documentDTOs == null) {
            documentDTOs = new ArrayList<>();
            documentListDTO.setList(documentDTOs);
        }

        //add the required range of objects to be returned
        int start = offset < documentations.size() && offset >= 0 ? offset : Integer.MAX_VALUE;
        int end = offset + limit - 1 <= documentations.size() - 1 ? offset + limit - 1 : documentations.size() - 1;
        for (int i = start; i <= end; i++) {
            documentDTOs.add(fromDocumentationToDTO(documentations.get(i)));
        }
        documentListDTO.setCount(documentDTOs.size());
        return documentListDTO;
    }

    /**
     * Sets pagination urls for a DocumentListDTO object given pagination parameters and url parameters
     *
     * @param documentListDTO a DocumentListDTO object
     * @param limit           max number of objects returned
     * @param offset          starting index
     * @param size            max offset
     */
    public static void setPaginationParams(DocumentListDTO documentListDTO, String apiId, int offset, int limit,
                                           int size) {

//        //acquiring pagination parameters and setting pagination urls
//        Map<String, Integer> paginatedParams = RestApiUtil.getPaginationParams(offset, limit, size);
//        String paginatedPrevious = "";
//        String paginatedNext = "";
//
//        if (paginatedParams.get(RestApiConstants.PAGINATION_PREVIOUS_OFFSET) != null) {
//            paginatedPrevious = RestApiUtil
//                    .getDocumentationPaginatedURL(paginatedParams.get(RestApiConstants.PAGINATION_PREVIOUS_OFFSET),
//                            paginatedParams.get(RestApiConstants.PAGINATION_PREVIOUS_LIMIT), apiId);
//        }
//
//        if (paginatedParams.get(RestApiConstants.PAGINATION_NEXT_OFFSET) != null) {
//            paginatedNext = RestApiUtil
//                    .getDocumentationPaginatedURL(paginatedParams.get(RestApiConstants.PAGINATION_NEXT_OFFSET),
//                            paginatedParams.get(RestApiConstants.PAGINATION_NEXT_LIMIT), apiId);
//        }
//
//        documentListDTO.setNext(paginatedNext);
//        documentListDTO.setPrevious(paginatedPrevious);
    }
}
