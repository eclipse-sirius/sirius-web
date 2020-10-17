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

export const MoreHorizontal = ({ title, ...props }) => {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      viewBox="0 0 24 6"
      aria-labelledby="title"
      aria-describedby="desc"
      role="img"
      {...props}>
      <title>{title}</title>
      <path d="M2.66667 0C1.2 0 0 1.2 0 2.66667C0 4.13333 1.2 5.33333 2.66667 5.33333C4.13333 5.33333 5.33333 4.13333 5.33333 2.66667C5.33333 1.2 4.13333 0 2.66667 0ZM21.3333 0C19.8667 0 18.6667 1.2 18.6667 2.66667C18.6667 4.13333 19.8667 5.33333 21.3333 5.33333C22.8 5.33333 24 4.13333 24 2.66667C24 1.2 22.8 0 21.3333 0ZM12 0C10.5333 0 9.33333 1.2 9.33333 2.66667C9.33333 4.13333 10.5333 5.33333 12 5.33333C13.4667 5.33333 14.6667 4.13333 14.6667 2.66667C14.6667 1.2 13.4667 0 12 0Z"></path>
    </svg>
  );
};
MoreHorizontal.propTypes = propTypes;
