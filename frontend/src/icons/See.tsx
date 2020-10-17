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

export const See = ({ title, ...props }) => {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      viewBox="0 0 24 24"
      aria-labelledby="title"
      aria-describedby="desc"
      role="img"
      {...props}>
      <title>{title}</title>
      <path d="M0 11.9824C1.04261 6.30354 6.01941 2 12 2C17.9806 2 22.9563 6.30354 24 11.9824C22.9574 17.6613 17.9806 21.9649 12 21.9649C6.01941 21.9649 1.04372 17.6613 0 11.9824V11.9824ZM12 17.5282C13.4708 17.5282 14.8814 16.9439 15.9215 15.9039C16.9615 14.8639 17.5458 13.4533 17.5458 11.9824C17.5458 10.5116 16.9615 9.10101 15.9215 8.06097C14.8814 7.02093 13.4708 6.43664 12 6.43664C10.5292 6.43664 9.11857 7.02093 8.07853 8.06097C7.03849 9.10101 6.4542 10.5116 6.4542 11.9824C6.4542 13.4533 7.03849 14.8639 8.07853 15.9039C9.11857 16.9439 10.5292 17.5282 12 17.5282ZM12 15.3099C11.1175 15.3099 10.2711 14.9593 9.64712 14.3353C9.02309 13.7113 8.67252 12.8649 8.67252 11.9824C8.67252 11.0999 9.02309 10.2536 9.64712 9.62955C10.2711 9.00553 11.1175 8.65496 12 8.65496C12.8825 8.65496 13.7289 9.00553 14.3529 9.62955C14.9769 10.2536 15.3275 11.0999 15.3275 11.9824C15.3275 12.8649 14.9769 13.7113 14.3529 14.3353C13.7289 14.9593 12.8825 15.3099 12 15.3099Z"></path>
    </svg>
  );
};
See.propTypes = propTypes;
