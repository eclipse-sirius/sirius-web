/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
import React from 'react';
import PropTypes from 'prop-types';

import { Text } from 'core/text/Text';
import { OnboardArea } from './OnboardArea';
import { DiagramWebSocketContainer } from 'diagram/DiagramWebSocketContainer';
import { RepresentationNavigation } from 'views/edit-project/RepresentationNavigation';

import styles from './RepresentationArea.module.css';

const propTypes = {
  representations: PropTypes.array.isRequired,
  selections: PropTypes.array.isRequired,
  displayedRepresentation: PropTypes.object,
  setSelections: PropTypes.func.isRequired,
  setSubscribers: PropTypes.func.isRequired,
};
export const RepresentationArea = ({
  representations,
  selections,
  displayedRepresentation,
  setSelections,
  setSubscribers,
}) => {
  let content;
  if (!displayedRepresentation) {
    content = <OnboardArea selections={selections} setSelections={setSelections} />;
  } else if (displayedRepresentation.kind === 'Diagram') {
    content = (
      <DiagramWebSocketContainer
        representationId={displayedRepresentation.id}
        selections={selections}
        setSelections={setSelections}
        setSubscribers={setSubscribers}
      />
    );
  } else {
    content = <Text className={styles.text}>Invalid representation type ${displayedRepresentation.kind}</Text>;
  }

  return (
    <div className={styles.representationArea}>
      <RepresentationNavigation
        representations={representations}
        displayedRepresentation={displayedRepresentation}
        setSelections={setSelections}
      />
      {content}
    </div>
  );
};
RepresentationArea.propTypes = propTypes;
