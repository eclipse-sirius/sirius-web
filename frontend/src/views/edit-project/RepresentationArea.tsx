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
import { Text } from 'core/text/Text';
import { DiagramWebSocketContainer } from 'diagram/DiagramWebSocketContainer';
import { FormWebSocketContainer } from 'form/FormWebSocketContainer';
import PropTypes from 'prop-types';
import React from 'react';
import { RepresentationNavigation } from 'views/edit-project/RepresentationNavigation';
import { OnboardArea } from './OnboardArea';
import styles from './RepresentationArea.module.css';

const propTypes = {
  projectId: PropTypes.string.isRequired,
  readOnly: PropTypes.bool.isRequired,
  representations: PropTypes.array.isRequired,
  selection: PropTypes.object,
  displayedRepresentation: PropTypes.object,
  setSelection: PropTypes.func.isRequired,
  setSubscribers: PropTypes.func.isRequired,
};
export const RepresentationArea = ({
  projectId,
  representations,
  readOnly,
  selection,
  displayedRepresentation,
  setSelection,
  setSubscribers,
}) => {
  let content;
  if (!displayedRepresentation) {
    content = <OnboardArea projectId={projectId} selection={selection} setSelection={setSelection} />;
  } else if (displayedRepresentation.kind === 'Diagram') {
    content = (
      <DiagramWebSocketContainer
        projectId={projectId}
        representationId={displayedRepresentation.id}
        readOnly={readOnly}
        selection={selection}
        setSelection={setSelection}
        setSubscribers={setSubscribers}
      />
    );
  } else if (displayedRepresentation.kind === 'Form') {
    content = <FormWebSocketContainer projectId={projectId} formId={displayedRepresentation.id} />;
  } else {
    content = <Text className={styles.text}>Invalid representation type ${displayedRepresentation.kind}</Text>;
  }

  return (
    <div className={styles.representationArea} data-testid="representation-area">
      <RepresentationNavigation
        representations={representations}
        displayedRepresentation={displayedRepresentation}
        setSelection={setSelection}
      />
      {content}
    </div>
  );
};
RepresentationArea.propTypes = propTypes;
