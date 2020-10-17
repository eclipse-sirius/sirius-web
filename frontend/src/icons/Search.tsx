/*******************************************************************************
 * Copyright (c) 2020 Remix Design Studio, Obeo and others
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 *
 * Contributors:
 *     Remix Design Studio - initial implementation as SVG
 *     Obeo - conversion into JSX
 *******************************************************************************/
import React from 'react';
import PropTypes from 'prop-types';

const propTypes = {
  title: PropTypes.string.isRequired,
};

export const Search = ({ title, ...props }) => {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      viewBox="0 0 24 24"
      aria-labelledby="title"
      aria-describedby="desc"
      role="img"
      {...props}>
      <title>{title}</title>
      <g clip-path="url(#clip0)">
        <path d="M18.94 17.27L24 22.327 22.328 24l-5.059-5.06a10.585 10.585 0 01-6.636 2.326C4.763 21.266 0 16.503 0 10.633 0 4.763 4.764 0 10.633 0c5.87 0 10.633 4.764 10.633 10.633a10.585 10.585 0 01-2.326 6.636zm-2.37-.877a8.243 8.243 0 002.333-5.76c0-4.57-3.701-8.27-8.27-8.27-4.57 0-8.27 3.7-8.27 8.27 0 4.569 3.7 8.27 8.27 8.27a8.243 8.243 0 005.76-2.333l.177-.177z"></path>
      </g>
      <defs>
        <clipPath id="clip0">
          <path d="M0 0H24V24H0z"></path>
        </clipPath>
      </defs>
    </svg>
  );
};
Search.propTypes = propTypes;
