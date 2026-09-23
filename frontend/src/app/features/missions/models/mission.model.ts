export type MissionType =
  | 'INTERNAL' | 'EVA' | 'SCIENCE' | 'LOGISTICS'
  | 'MAINTENANCE' | 'RESCUE' | 'INSPECTION' | 'EXPLORATION' | 'TRANSPORT';

export type MissionPriority = 'LOW' | 'MEDIUM' | 'HIGH' | 'CRITICAL';
export type MissionSafetyLevel = 'STANDARD' | 'ELEVATED' | 'HIGH_RISK' | 'CRITICAL';
export type MissionStatus = 'DRAFT';

export interface MissionCreateRequest {
  title: string;
  description?: string;
  missionType: MissionType;
  idOriginColony: number;
  idDestinationColony?: number | null;
  destinationName?: string;
  plannedStartAt: string;
  plannedEndAt: string;
  priority: MissionPriority;
  safetyLevel: MissionSafetyLevel;
}

export interface MissionCreateResponse {
  idMission: number;
  code: string;
  status: MissionStatus;
  title: string;
  createdAt: string;
}

export interface MissionResponse {
  idMission: number;
  code: string;
  status: MissionStatus;
  title: string;
  description?: string;
  missionType: MissionType;
  idOriginColony: number;
  idDestinationColony?: number;
  destinationName?: string;
  plannedStartAt: string;
  plannedEndAt: string;
  priority: MissionPriority;
  safetyLevel: MissionSafetyLevel;
  createdAt: string;
}
